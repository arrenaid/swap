import org.apache.log4j.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Класс для подкачки неких параметров из файла properties.
 * @author arrenaid
 * @version 1.1
 */
public class PropertiesUpdate {
    private static String rootPath = "F:\\IdeaProject\\swap\\src\\main\\resources\\";//Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String configPath = rootPath + "daedalus.properties";

    @Property(propertyName = "com.mycompany.name", propertyDefault = "upiter")
    private static String myCompanyName;
    @Property(propertyName = "com.mycompany.owner")
    private static String myCompanyOwner;
    @Property(propertyName = "com.mycompany.years.old", propertyDefault = "333")
    private static int myCompanyYears;
    @Property(propertyName = "com.mycompany.address")
    private static Address address;
    @Property(propertyName = "com.mycompany.capitalization", propertyDefault = "222")
    private static double lastYearsCapitalization;
    @Property(propertyName = "com.mycompany.employees")
    private static int myCompanyNumberOfEmployees;
    private static String emptyFieldPropertiesUpdate = "empty";

    /**
     * Находит все поля в классе помечанные аннотацией @Property. Заполняет эти поля соответствующими значениеми лежащими в файле под именем configName.
     * @param obj - экземпляр класса PropertiesUpdate.
     * @param configName - имя фала конфигурации.
     */
    public static synchronized void doRefresh(Object obj, String configName){
        if(configName!=null){
            configPath = rootPath + configName;
        }
        Properties propsCompany = new Properties();
        try {
            propsCompany.load(new FileInputStream(configPath));
            Main.logger.log(Level.INFO, "dR open: " + configPath);
        } catch (IOException e) {
            Main.logger.error(e);
            return;
        }
        Field [] fields = obj.getClass().getDeclaredFields();
        for(Field field: fields){
            Annotation [] annotations = field.getDeclaredAnnotations();
            System.out.println(field);
            for(Annotation annotation: annotations){
                if(annotation instanceof Property property){
                    System.out.println("\tаннотация: " + property.propertyName() + ":\t"+ propsCompany.getProperty(property.propertyName(), property.propertyDefault()));
                    field.setAccessible(true);
                    try {
                        changeField(field, propsCompany.getProperty(property.propertyName(), property.propertyDefault()),obj);
                    } catch (IllegalAccessException e) {
                        Main.logger.error(e);
                    }
                    finally {
                        field.setAccessible(false);
                    }
                }
            }
        }
    }

    /**
     * Изменяет поле класса значениями из файла или значениями по умолчанию.
     * @param field - поле для замены.
     * @param str - значение полученное из файла.
     * @param obj - экземпляр класса.
     * @throws IllegalAccessException
     */
    private static void changeField( Field field, String str, Object obj) throws IllegalAccessException {
        Object before = field.get(field);
        if (field.getType().equals(String.class)){
                field.set(obj, str);
        }else if (field.getType().equals(int.class)){
            if (!str.equals("null")) {
                field.setInt(obj, Integer.parseInt(str));
            } else {
                field.setInt(obj, 0);
            }
        }else if (field.getType().equals(double.class)){
            if (!str.equals("null")) {
                field.setDouble(obj, Double.parseDouble(str));
            } else {
                field.setDouble(obj, 0);
            }
        }else if (field.getType().equals(Address.class)){
            field.set(obj,readJsonWriteAddress(str));
        }
        Main.logger.log(Level.INFO,"cF change: field: " + field.getName() +
                " before: " + before + " after: " + field.get(field));
    }

    /**
     * Создает экземпляр класса Address и заполняет параметрами полученными из файла. Если парметры неверные, возвращает пустой класс.
     * @param str - значение полученое из файла.
     * @return класс Address.
     */
    private static Address readJsonWriteAddress(String str){
        Map<String, String> map = new HashMap<>();
        String replace = str.replace("{", "");
        String replace1 = replace.replace("}", "");
        String replace2 = replace1.replace(" ", "");
        String[] strSplit = replace2.split(",");
        try {
            for (String element : strSplit) {
                String[] split = element.split(":");
                map.put(split[0], split[1]);
            }
            return new Address(map.get("street"),Integer.valueOf(map.get("home")));
        }catch (Exception e){
            Main.logger.error("rJWA: " + str,e);
            return new Address();
        }
    }

    /**
     * Записывает в логгер значение всех полей класса PropertiesUpdate.
     */
    public static void printAll(){
        String print = " s - name - " + myCompanyName +
                "\n\told " + myCompanyYears+
                "\n\town " + myCompanyOwner +
                "\n\tadr " + address +
                "\n\tcap " + lastYearsCapitalization +
                "\n\temp " + myCompanyNumberOfEmployees +
                "\n\tmpt " + emptyFieldPropertiesUpdate +" - f\n";
        Main.logger.log(Level.INFO,print);
    }

    /**
     * Метод для просмотра защищенного поля.
     * @return возвращает имя владельца.
     */
    public static String getMyCompanyOwner() {
        return myCompanyOwner;
    }
}
