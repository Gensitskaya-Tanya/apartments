import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	private String connection;
	private String user;
	private String password;

	public DatabaseConnection() {
		readConnectionParameter();
	}

	private final void readConnectionParameter() {
		Properties props = new Properties();
		try (InputStream fis = getClass().getResourceAsStream("/config.properties")) {
			props.load(fis);
			connection = (String) props.get("DB_CONNECTION");
			user = (String) props.get("DB_USER");
			password = (String) props.get("DB_PASSWORD");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection connection() throws SQLException {
		return  DriverManager.getConnection(connection, user, password);
	}




	public String getConnection() {
		return connection;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
