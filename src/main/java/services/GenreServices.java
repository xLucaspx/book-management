package services;

import exceptions.NotFoundException;
import models.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Service class for manipulating the database regarding {@link Genre genres}.
 *
 * @author Lucas da Paz
 */
public class GenreServices extends Services<Genre> {

	/**
	 * Constructs an instance of {@link GenreServices}.
	 *
	 * @param connection A connection with the systems database,
	 *                   as provided by the data source.
	 */
	public GenreServices(Connection connection) {
		super(connection);
	}

	/**
	 * Searches the database for all {@link Genre} records whose name
	 * matches the string passed as an argument; uses SQL {@code LIKE}.
	 *
	 * @param name Filter string.
	 * @return A set of all occurrences of genre whose queried attribute
	 * matches the value passed as an argument.
	 */
	public Set<Genre> filterByName(String name) {
		String sql = "SELECT `id`, `name`, countbooksbygenre(`id`) AS `books_owned` FROM `genre` WHERE `name` LIKE ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%%%s%%".formatted(name));
			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Genre getById(int id) {
		String sql = "SELECT `id`, `name`, countbooksbygenre(`id`) AS `books_owned` FROM `genre` WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			Set<Genre> genres = transformToSet(ps);

			if (genres.isEmpty()) throw new NotFoundException("Nenhum gênero encontrado para o id: %d".formatted(id));

			return genres.toArray(new Genre[1])[0];
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<Genre> getAll() {
		String sql = "SELECT `id`, `name`, countbooksbygenre(`id`) AS `books_owned` FROM `genre`;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Genre create(Genre genre) {
		String sql = "INSERT INTO `genre` (`name`) VALUES (?);";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			populateStatement(ps, genre);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 0) throw new SQLException("Falha ao criar gênero, nenhuma linha do banco afetada!");

			int genreId = getGeneratedId(ps);
			return getById(genreId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Genre update(Genre genre) {
		String sql = "UPDATE `genre` SET `name` = ? WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			populateStatement(ps, genre);
			ps.setInt(2, genre.getId());
			ps.execute();

			return getById(genre.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM `genre` WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Set<Genre> transformToSet(PreparedStatement ps) throws SQLException {
		Set<Genre> genres = new HashSet<>();

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int booksOwned = rs.getInt("books_owned");

				Genre genre = new Genre(id, name, booksOwned);
				genres.add(genre);
			}
			return Collections.unmodifiableSet(genres);
		}
	}

	@Override
	protected void populateStatement(PreparedStatement ps, Genre genre) throws SQLException {
		ps.setString(1, genre.getName());
	}
}
