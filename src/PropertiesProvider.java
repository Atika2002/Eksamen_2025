import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {
    public static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(new FileInputStream("db.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Fant ikke properties "+e.getMessage());
        }
    }
}
