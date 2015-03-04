import java.io.*;
import java.util.Properties;

public class ConfigStore {
    private File configFile;
    private Properties properties;

    public ConfigStore() {

        try {

            this.configFile = new File(System.getProperty("user.home") + "/.eazytweet/config.properties");

            if (!this.configFile.isFile()) {
                // Create File if not exists
                this.configFile.getParentFile().mkdirs();
                this.configFile.createNewFile();
            }

            this.properties = new Properties();

            this.properties.load(new FileInputStream(this.configFile.getAbsoluteFile()));

            set("yolo", "swag");

            System.out.println(this.properties);

        } catch (IOException e) {
            // TODO: Handle exception
            e.printStackTrace();
        }
    }

    public String get(String name) {
        return this.properties.getProperty(name);
    }

    public void set(String name, String value) {
        try {

            this.properties.setProperty(name, value);
            this.properties.store(new FileOutputStream(this.configFile.getAbsoluteFile()), "Auto generated by EazyTweet");

        } catch (FileNotFoundException e) {
            // TODO: Handle exception
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: Handle exception
            e.printStackTrace();
        }
    }
}
