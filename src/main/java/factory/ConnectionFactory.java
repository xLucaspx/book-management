package factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class responsible for connecting to the database.
 *
 * @author Lucas da Paz
 */
public class ConnectionFactory {
	private final static String USERNAME = "user01";
	private final static String PASSWORD = "admin";
	private final static String DATABASE = "book_management";

	/**
	 * @return The {@link Connection} obtained from the application
	 * data source.
	 */
	public Connection getConnection() {
		try {
			return createDataSource().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a {@link DataSource} instance using the credentials provided
	 * in this class ({@link #USERNAME}, {@link #PASSWORD} and {@link #DATABASE}.
	 *
	 * @return The initialized data source.
	 */
	private DataSource createDataSource() throws SQLException {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/%s".formatted(DATABASE));
		config.setUsername(USERNAME);
		config.setPassword(PASSWORD);
		config.setMaximumPoolSize(5);

		return new HikariDataSource(config);
	}
}
