import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("START: " + LocalDateTime.now());
        PropertiesUpdate propertiesUpdate = new PropertiesUpdate();
        propertiesUpdate.printAll();
        if(args.length >0)
            PropertiesUpdate.doRefresh(propertiesUpdate,args[0]);
        else
            PropertiesUpdate.doRefresh(propertiesUpdate,null);
        propertiesUpdate.printAll();
    }
}
