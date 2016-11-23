package email.notification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailConfiguration {
	private String path;

	private static EmailConfiguration configuration;

	private EmailConfiguration() {
		path = "config.properties";
	}

	public static synchronized EmailConfiguration getInstance() {
		if (configuration == null) {
			configuration = new EmailConfiguration();
		}
		return configuration;
	}

	public String getConfigPath() {
		return path;
	}

	public Properties getConfigProperties() {
		Properties props = new Properties();
		try {
			InputStream fis = new FileInputStream(path);
			props.load(fis);
		} catch (IOException e) {
			System.out.print("Configuration file not found!" + e.getMessage());
		}

		return props;
	}
}