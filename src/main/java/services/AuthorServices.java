package services;

import exceptions.NotFoundException;
import models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Service class for manipulating the database regarding {@link Author authors}.
 *
 * @author Lucas da Paz
 */
public class AuthorServices extends Services<Author> {

	/**
	 * Constructs an instance of {@link AuthorServices}.
	 *
	 * @param connection A connection with the systems database,
	 *                   as provided by the data source.
	 */
	public AuthorServices(Connection connection) {
		super(connection);
	}

	/**
	 * Searches the database for all {@link Author} records whose name
	 * matches the string passed as an argument; uses SQL {@code LIKE}.
	 *
	 * @param name Filter string.
	 * @return A {@link Set} of all occurrences of author whose queried attribute
	 * matches the value passed as an argument.
	 */
	public Set<Author> filterByName(String name) {
		String sql = """
				SELECT
					`id`, `name`, `nationality`, countbooksbyauthor(`id`) AS `books_owned`
				FROM `author`
				WHERE `name` LIKE ?
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%%%s%%".formatted(name));
			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Searches the database for all {@link Author} records whose nationality
	 * matches the string passed as an argument; uses SQL {@code LIKE}.
	 *
	 * @param nationality Filter string.
	 * @return A {@link Set} of all occurrences of author whose queried attribute
	 * matches the value passed as an argument.
	 */
	public Set<Author> filterByNationality(String nationality) {
		String sql = """
				SELECT
					`id`, `name`, `nationality`, countbooksbyauthor(`id`) AS `books_owned`
				FROM `author`
				WHERE `nationality` LIKE ?
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%%%s%%".formatted(nationality));
			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Author getById(int id) {
		String sql =
			"SELECT `id`, `name`, `nationality`, countbooksbyauthor(`id`) AS `books_owned` FROM `author` WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			Set<Author> authors = transformToSet(ps);

			if (authors.isEmpty()) throw new NotFoundException("Nenhum autor encontrado para o id: %d".formatted(id));

			return authors.toArray(new Author[1])[0];
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<Author> getAll() {
		String sql = "SELECT `id`, `name`, `nationality`, countbooksbyauthor(`id`) AS `books_owned` FROM `author`;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Author create(Author author) {
		String sql = "INSERT INTO `author` (`name`, `nationality`) VALUES (?, ?);";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			populateStatement(ps, author);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 0) throw new SQLException("Falha ao criar autor, nenhuma linha do banco afetada!");

			int authorId = getGeneratedId(ps);
			return getById(authorId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Author update(Author author) {
		String sql = "UPDATE `author` SET `name` = ?, `nationality` = ? WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			populateStatement(ps, author);
			ps.setInt(3, author.getId());

			ps.execute();
			return getById(author.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM `author` WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Set<Author> transformToSet(PreparedStatement ps) throws SQLException {
		Set<Author> authors = new HashSet<>();

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String nationality = rs.getString("nationality");
				int booksOwned = rs.getInt("books_owned");

				Author author = new Author(id, name, nationality, booksOwned);
				authors.add(author);
			}
			return Collections.unmodifiableSet(authors);
		}
	}

	@Override
	protected void populateStatement(PreparedStatement ps, Author author) throws SQLException {
		ps.setString(1, author.getName());
		ps.setString(2, author.getNationality());
	}
}
