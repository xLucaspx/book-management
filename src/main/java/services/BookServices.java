package services;

import exceptions.NotFoundException;
import models.Author;
import models.Book;
import models.Format;
import models.Genre;
import models.Publisher;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Service class for manipulating the database regarding {@link Book books}.
 *
 * @author Lucas da Paz
 */
public class BookServices extends Services<Book> {

	/**
	 * Constructs an instance of {@link BookServices}.
	 *
	 * @param connection A connection with the systems database,
	 *                   as provided by the data source.
	 */
	public BookServices(Connection connection) {
		super(connection);
	}

	/**
	 * Searches the database for all {@link Book} records whose title
	 * matches the string passed as an argument; uses SQL {@code LIKE}.
	 *
	 * @param title Filter string.
	 * @return A set of all occurrences of book whose queried attribute
	 * matches the value passed as an argument.
	 */
	public Set<Book> filterByTitle(String title) {
		String sql = """
			  SELECT
			    b.`id`, b.`title`, b.`isbn_10`, b.`isbn_13`, b.`pages`, b.`read`, b.`purchase_date`, b.`price`,
			    b.`format`, b.`author_id`, b.`publisher_id`, g.`genre_id`
			  FROM `book` b
			    INNER JOIN `book_genre` g ON b.`id` = g.`book_id`
			  WHERE b.`title` LIKE ?;
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%%%s%%".formatted(title));

			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Searches the database for all {@link Book} records whose author name
	 * matches the string passed as an argument; uses SQL {@code LIKE}.
	 *
	 * @param authorName Filter string.
	 * @return A set of all occurrences of book whose queried attribute
	 * matches the value passed as an argument.
	 */
	public Set<Book> filterByAuthor(String authorName) {
		String sql = """
			  SELECT
			    b.`id`, b.`title`, b.`isbn_10`, b.`isbn_13`, b.`pages`, b.`read`, b.`purchase_date`, b.`price`,
			    b.`format`, b.`author_id`, b.`publisher_id`, g.`genre_id`
			  FROM `book` b
			    INNER JOIN `book_genre` g ON b.`id` = g.`book_id`
			       INNER JOIN `author` a ON b.`author_id` = a.`id`
			  WHERE a.`name` LIKE ?;
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%%%s%%".formatted(authorName));

			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Searches the database for all {@link Book} records whose ISBN-10
	 * or ISBN-13 matches the string passed as an argument; uses SQL {@code LIKE}.
	 *
	 * @param isbn Filter string.
	 * @return A set of all occurrences of book whose queried attribute
	 * matches the value passed as an argument.
	 */
	public Set<Book> filterByIsbn(String isbn) {
		String sql = """
			  SELECT
			    b.`id`, b.`title`, b.`isbn_10`, b.`isbn_13`, b.`pages`, b.`read`, b.`purchase_date`, b.`price`,
			    b.`format`, b.`author_id`, b.`publisher_id`, g.`genre_id`
			  FROM `book` b
			    INNER JOIN `book_genre` g ON b.`id` = g.`book_id`
			  WHERE
						`isbn_10` LIKE ?
						OR `isbn_13` LIKE ?;
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%%%s%%".formatted(isbn));
			ps.setString(2, "%%%s%%".formatted(isbn));

			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Queries the database for all {@link Book} records whose {@link Author}
	 * is exactly the same as the one passed as an argument; identification
	 * is performed using primary keys.
	 *
	 * @param author The reference author.
	 * @return A set of all occurrences of book with the corresponding author.
	 */
	public Set<Book> getByAuthor(Author author) {
		String sql = """
			  SELECT
			    b.`id`, b.`title`, b.`isbn_10`, b.`isbn_13`, b.`pages`, b.`read`, b.`purchase_date`, b.`price`,
			    b.`format`, b.`author_id`, b.`publisher_id`, g.`genre_id`
			  FROM `book` b
			    INNER JOIN `book_genre` g ON b.`id` = g.`book_id`
			  WHERE b.`author_id` = ?;
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, author.getId());

			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Queries the database for all {@link Book} records whose {@link Publisher}
	 * is exactly the same as the one passed as an argument; identification
	 * is performed using primary keys.
	 *
	 * @param publisher The reference publisher.
	 * @return A set of all occurrences of book with the corresponding publisher.
	 */
	public Set<Book> getByPublisher(Publisher publisher) {
		String sql = """
			  SELECT
			    b.`id`, b.`title`, b.`isbn_10`, b.`isbn_13`, b.`pages`, b.`read`, b.`purchase_date`, b.`price`,
			    b.`format`, b.`author_id`, b.`publisher_id`, g.`genre_id`
			  FROM `book` b
			    INNER JOIN `book_genre` g ON b.`id` = g.`book_id`
			  WHERE b.`publisher_id` = ?;
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, publisher.getId());

			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Queries the database for all {@link Book} records whose {@link Genre}
	 * is exactly the same as the one passed as an argument; identification
	 * is performed using primary keys.
	 *
	 * @param genre The reference genre.
	 * @return A set of all occurrences of book with the corresponding genre.
	 */
	public Set<Book> getByGenre(Genre genre) {
		String sql = """
			  SELECT
			    b.`id`, b.`title`, b.`isbn_10`, b.`isbn_13`, b.`pages`, b.`read`, b.`purchase_date`, b.`price`,
			    b.`format`, b.`author_id`, b.`publisher_id`, g.`genre_id`
			  FROM `book` b
			    INNER JOIN `book_genre` g ON b.`id` = g.`book_id`
			  WHERE g.`genre_id` = ?;
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, genre.getId());

			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Inserts into the database a link between a {@link Book} and
	 * the {@link Genre} passed as arguments.
	 *
	 * @param bookId The ID of the book to link the genre to.
	 * @param genre  The genre to link.
	 */
	private void addGenre(int bookId, Genre genre) {
		String sql = "INSERT INTO `book_genre` (`book_id`, `genre_id`) VALUES (?, ?);";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, bookId);
			ps.setInt(2, genre.getId());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes form the database a link, if any, between the
	 * {@link Book} with corresponding ID and the {@link Genre}
	 * passed as argument.
	 *
	 * @param bookId The ID of the book to unlink the genre from.
	 * @param genre  The genre to unlink.
	 */
	public void removeGenre(int bookId, Genre genre) {
		String sql = "DELETE FROM `book_genre` WHERE `book_id` = ? AND `genre_id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, bookId);
			ps.setInt(2, genre.getId());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Updates the read status of the {@link Book} passed as an argument
	 * with the value returned from {@link Book#isRead}.
	 *
	 * @param book The book being updated.
	 */
	public void updateReadStatus(Book book) {
		String sql = "UPDATE `book` SET `read`= ? WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setBoolean(1, book.isRead());
			ps.setInt(2, book.getId());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Book getById(int id) {
		String sql = """
			  SELECT
			    b.`id`, b.`title`, b.`isbn_10`, b.`isbn_13`, b.`pages`, b.`read`, b.`purchase_date`, b.`price`,
			    b.`format`, b.`author_id`, b.`publisher_id`, g.`genre_id`
			  FROM `book` b
			    INNER JOIN `book_genre` g ON b.`id` = g.`book_id`
			   WHERE b.`id` = ?;
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			Set<Book> books = transformToSet(ps);

			if (books.isEmpty()) throw new NotFoundException("Nenhum livro encontrado para o id: %d".formatted(id));

			return books.toArray(new Book[1])[0];
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<Book> getAll() {
		String sql = """
			  SELECT
			    b.`id`, b.`title`, b.`isbn_10`, b.`isbn_13`, b.`pages`, b.`read`, b.`purchase_date`, b.`price`,
			    b.`format`, b.`author_id`, b.`publisher_id`, g.`genre_id`
			  FROM `book` b
			    INNER JOIN `book_genre` g ON b.`id` = g.`book_id`;
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			return transformToSet(ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc} Links the created {@link Book} to each {@link Genre} returned
	 * by {@link Book#getGenres()}.
	 *
	 * @param book The instance of book to be inserted into the database.
	 * @return {@inheritDoc}
	 */
	@Override
	public Book create(Book book) {
		String sql = """
				INSERT INTO `book`
					(`title`, `isbn_10`, `isbn_13`, `pages`, `read`, `format`, `author_id`, `publisher_id`, `purchase_date`, `price`)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
			""";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			populateStatement(ps, book);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 0) throw new SQLException("Falha ao criar livro, nenhuma linha do banco afetada");

			int bookId = getGeneratedId(ps);

			book.getGenres().forEach(g -> addGenre(bookId, g));

			return getById(bookId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc} Retrieves all {@link Genre} occurrences linked to the
	 * {@link Book} in the database and compares them with those returned by
	 * the {@link Book#getGenres()} method of the book passed as an argument;
	 * removes any existing links between those that appear only in the former
	 * and creates new links for those that appear only in the latter. If a genre
	 * appears in both sets, no changes are necessary.
	 *
	 * @param book The instance of {@link Book} to be updated in the database.
	 * @return {@inheritDoc}
	 */
	@Override
	public Book update(Book book) {
		String sql = """
			UPDATE `book`
			 SET `title` = ?, `isbn_10` = ?, `isbn_13` = ?, `pages` = ?, `read` = ?,
			 `format` = ?, `author_id` = ?, `publisher_id` = ?, `purchase_date` = ?, `price` = ?
			WHERE id = ?;
			""";
		int bookId = book.getId();

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			populateStatement(ps, book);
			ps.setInt(11, bookId);
			ps.execute();

			Set<Genre> oldGenres = getById(bookId).getGenres();
			Set<Genre> newGenres = new HashSet<>(book.getGenres());

			for (Genre g : oldGenres) {
				if (newGenres.contains(g)) {
					newGenres.remove(g);
					continue;
				}
				removeGenre(bookId, g);
			}

			newGenres.forEach(g -> addGenre(bookId, g));

			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM `book` WHERE `id` = ?;";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Set<Book> transformToSet(PreparedStatement ps) throws SQLException {
		Map<Integer, Book> books = new HashMap<>();

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				int genreId = rs.getInt("genre_id");
				Genre genre = new GenreServices(connection).getById(genreId);

				if (books.containsKey(id)) {
					books.get(id).addGenre(genre);
					continue;
				}

				String title = rs.getString("title");
				String isbn10 = rs.getString("isbn_10");
				String isbn13 = rs.getString("isbn_13");
				int pages = rs.getInt("pages");
				boolean read = rs.getBoolean("read");
				var purchaseDate = rs.getDate("purchase_date");
				float price = rs.getFloat("price");
				String formatName = rs.getString("format");
				int authorId = rs.getInt("author_id");
				int publisherId = rs.getInt("publisher_id");

				Author author = new AuthorServices(connection).getById(authorId);
				Publisher publisher = new PublisherServices(connection).getById(publisherId);
				Format format = Format.valueOf(formatName.toUpperCase());

				Book book = new Book(id, title, isbn13, pages, read, format, author, publisher, price);

				if (isbn10 != null) book.setIsbn10(isbn10);
				if (purchaseDate != null) book.setPurchaseDate(purchaseDate.toLocalDate());

				book.addGenre(genre);
				books.put(id, book);
			}

			return Set.copyOf(books.values());
		}
	}

	@Override
	protected void populateStatement(PreparedStatement ps, Book book) throws SQLException {
		ps.setString(1, book.getTitle());
		ps.setString(2, book.getIsbn10());
		ps.setString(3, book.getIsbn13());
		ps.setInt(4, book.getPages());
		ps.setBoolean(5, book.isRead());
		ps.setInt(6, book.getFormat().getCode());
		ps.setInt(7, book.getAuthor().getId());
		ps.setInt(8, book.getPublisher().getId());
		ps.setDate(9, Date.valueOf(book.getPurchaseDate()));
		ps.setFloat(10, book.getPrice());
	}
}
