package services;

import exceptions.NotFoundException;
import models.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Service class for manipulating the database regarding {@link Publisher publishers}.
 *
 * @author Lucas da Paz
 */
public class PublisherServices extends Services<Publisher> {

	/**
	 * Constructs an instance of {@link PublisherServices}.
	 *
	 * @param connection A connection with the systems database,
	 *                   as provided by the data source.
	 */
	public PublisherServices(Connection connection) {
		super(connection);
	}

	/**
	 * Searches the database for all {@link Publisher} records whose name
	 * matches the string passed as an argument; uses SQL {@code LIKE}.
	 *
	 * @param name Filter string.
	 * @return A set of all occurrences of publisher whose queried attribute
	 * matches the value passed as an argument.
	 */
	public Set<Publisher> filterByName(String name) {
		String sql =
			"SELECT `id`, `name`, countbooksbypublisher(`id`) AS `books_owned` FROM `publisher` WHERE `name` LIKE ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%%%s%%".formatted(name));
			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Publisher getById(int id) {
		String sql = "SELECT `id`, `name`, countbooksbypublisher(`id`) AS `books_owned` FROM `publisher` WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			Set<Publisher> publishers = transformToSet(ps);

			if (publishers.isEmpty()) throw new NotFoundException("Nenhuma editora encontrada para o id: %d".formatted(id));

			return publishers.toArray(new Publisher[1])[0];
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<Publisher> getAll() {
		String sql = "SELECT `id`, `name`, countbooksbypublisher(`id`) AS `books_owned` FROM `publisher`;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Publisher create(Publisher publisher) {
		String sql = "INSERT INTO `publisher` (`name`) VALUES (?);";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			populateStatement(ps, publisher);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 0) throw new SQLException("Falha ao criar editora, nenhuma linha do banco afetada!");

			int publisherId = getGeneratedId(ps);
			return getById(publisherId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Publisher update(Publisher publisher) {
		String sql = "UPDATE `publisher` SET `name` = ? WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			populateStatement(ps, publisher);
			ps.setInt(2, publisher.getId());
			ps.execute();

			return getById(publisher.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM `publisher` WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Set<Publisher> transformToSet(PreparedStatement ps) throws SQLException {
		Set<Publisher> publishers = new HashSet<>();

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int booksOwned = rs.getInt("books_owned");

				Publisher publisher = new Publisher(id, name, booksOwned);
				publishers.add(publisher);
			}
			return Collections.unmodifiableSet(publishers);
		}
	}

	@Override
	protected void populateStatement(PreparedStatement ps, Publisher publisher) throws SQLException {
		ps.setString(1, publisher.getName());
	}
}
