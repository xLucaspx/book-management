package services;

import exceptions.NotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Abstract class that encapsulates behavior and attributes of services classes.
 *
 * @param <T> Type of the objects with which this class works.
 * @author Lucas da Paz
 */
public abstract class Services<T> {
	protected Connection connection;

	/**
	 * Initializes a service class.
	 *
	 * @param connection A connection with the systems database,
	 *                   as provided by the data source.
	 */
	public Services(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Retrieves the auto-generated ID from the statement passed as argument.
	 *
	 * @param statement The statement to retrieve the key from.
	 * @return The auto-generated ID as an {@code int}.
	 * @throws SQLException if a database access error occurs, if the statement
	 *                      is closed, if the statements result set is closed or
	 *                      if no key was found.
	 */
	protected int getGeneratedId(PreparedStatement statement) throws SQLException {
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) return generatedKeys.getInt(1);

			throw new SQLException("Falha ao criar, nenhum ID obtido!");
		}
	}

	/**
	 * Query the database for a single occurrence of data whose
	 * ID equals the one passed as an argument.
	 *
	 * @param id The ID of the desired occurrence.
	 * @return The instance of the object of type {@code T} with the corresponding ID.
	 * @throws NotFoundException If no occurrence of author matches the specified ID.
	 */
	public abstract T getById(int id);

	/**
	 * @return A {@link Set} of all occurrences of data of the specified type {@code T}.
	 */
	public abstract Set<T> getAll();

	/**
	 * Creates a new record in the database representing the object passed
	 * as an argument.
	 *
	 * @param object The instance of the object to be inserted into the database.
	 * @return The created object, with its primary key.
	 */
	public abstract T create(T object);

	/**
	 * Updates a record in the database to represent the current state of
	 * the object passed as an argument; the updated occurrence is the one whose
	 * primary key matches the object key.
	 *
	 * @param object The instance of the object to be updated in the database.
	 * @return The updated object.
	 */
	public abstract T update(T object);

	/**
	 * Deletes from the database the record whose primary key is equal to the
	 * one passed as an argument.
	 *
	 * @param id The ID of the record to be deleted.
	 */
	public abstract void delete(int id);

	/**
	 * Executes the {@link PreparedStatement} received as an argument, iterates
	 * over the generated {@link ResultSet} retrieving each record and instantiating
	 * the corresponding object, which is added to a {@link Set} to be returned after
	 * reaching the end of the result set.
	 *
	 * @param ps The {@code PreparedStatement} to be executed and generate the {@code ResultSet}.
	 * @return A {@code Set} with objects representing each row of the {@code ResultSet} generated
	 * by the execution of the {@code PreparedStatement}.
	 * @throws SQLException If a database access error occurs, if the {@code PreparedStatement}
	 *                      is closed, if it does not return a {@code ResultSet} or if the
	 *                      {@code ResultSet} is closed.
	 */
	protected abstract Set<T> transformToSet(PreparedStatement ps) throws SQLException;

	/**
	 * Populates a {@link PreparedStatement} with all fields required for both the {@link #create}
	 * and {@link #update} methods (this means that it will not populate the ID parameter for the
	 * update method).
	 *
	 * @param ps     The {@code PreparedStatement} to be populated.
	 * @param object The instance of the object that contains the required data.
	 * @throws SQLException If a database access error occurs, if the {@code PreparedStatement}
	 *                      is closed or if the if the {@code parameterIndex} in the implementation
	 *                      does not correspond to a parameter marker in the SQL statement.
	 */
	protected abstract void populateStatement(PreparedStatement ps, T object) throws SQLException;
}
