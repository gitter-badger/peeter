package model;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.*;
import java.util.Properties;

public class ConfigStore {
    private static final String ENCRYPTION_PASSWORD = "123456789";
    StandardPBEStringEncryptor encryptor;

    private File configFile;
	private Properties properties;

	public ConfigStore() {

		try {
            // Setup the encryptor
            this.encryptor = new StandardPBEStringEncryptor();
			this.encryptor.setPassword(ENCRYPTION_PASSWORD);

            this.configFile = new File(System.getProperty("user.home")
					+ "/.peeter/config.properties");
			boolean fileIsNew = false;

			if (!this.configFile.isFile()) {
				fileIsNew = true;
				// Create File if not exists
				this.configFile.getParentFile().mkdirs();
				this.configFile.createNewFile();
			}

			this.properties = new Properties();
			this.properties.load(new FileInputStream(this.configFile
					.getAbsoluteFile()));

			if (fileIsNew) {
				// Set Default Data in Config File
				this.set("locale.language", "de");
				this.set("locale.country", "DE");
			}

		} catch (IOException e) {
			// TODO: Handle exception
			e.printStackTrace();
		}
	}

	public String get(String name) {
		try {

			this.properties.load(new FileInputStream(this.configFile.getAbsoluteFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
        String encryptedProperty = this.properties.getProperty(name);

        return this.encryptor.decrypt(encryptedProperty);
	}

	public void set(String name, String value) {
		try {

			this.properties.setProperty(name, this.encryptor.encrypt(value));
			this.properties.store(
					new FileOutputStream(this.configFile.getAbsoluteFile()),
					"Auto generated by Peeter, Encrypted by Jasyp");

		} catch (FileNotFoundException e) {
			// TODO: Handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: Handle exception
			e.printStackTrace();
		}
	}

	public void unset(String name) {
		try {
			this.properties.remove(name);
			this.properties.store(
					new FileOutputStream(this.configFile.getAbsoluteFile()),
					"Auto generated by Peeter");
		} catch (IOException e) {
			// TODO: Handle exception
			e.printStackTrace();
		}
	}
}
