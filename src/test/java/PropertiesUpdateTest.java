import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertiesUpdateTest {

    @Test
    void doRefreshHelTest() {
        PropertiesUpdate.doRefresh(new PropertiesUpdate(),"helios.properties");
        assertEquals(PropertiesUpdate.getMyCompanyOwner(),"Helios");
    }
    @Test
    void doRefreshIcaTest() {
        PropertiesUpdate.doRefresh(new PropertiesUpdate(),"icarus.properties");
        assertEquals(PropertiesUpdate.getMyCompanyOwner(),"Icarus");
    }
    @Test
    void doRefreshDeeTest() {
        PropertiesUpdate.doRefresh(new PropertiesUpdate(),"daedalus.properties");
        assertEquals(PropertiesUpdate.getMyCompanyOwner(),"Daedalus");
    }
}
