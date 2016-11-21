package email.notification;

public class EmailConfiguration{
	private String path;
	
	private static EmailConfiguration configuration;
	
	private EmailConfiguration(){
		path = "config.properties";
	}
	
	public static synchronized EmailConfiguration getInstance(){
		if(configuration == null){
			configuration = new EmailConfiguration();
		}
		return configuration;
	}
	
	public String getConfigPath(){
		return path;
	}
}