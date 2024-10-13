package factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
	private final static String USERNAME = "user01";
	private final static String PASSWORD = "admin";
	private final static String DATABASE = "book_management";

	public Connection getConection() {
		try {
			return this.createDataSource().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private HikariDataSource createDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/%s".formatted(DATABASE));
		config.setUsername(USERNAME);
		config.setPassword(PASSWORD);
		config.setMaximumPoolSize(5);

		return new HikariDataSource(config);
	}
}
