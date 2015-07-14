package me.clip.ezblocks.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Database interface
 * 
 * @author Maximvdw
 */
public abstract class Database {
	protected final String PREFIX;
	protected boolean connected;
	protected Connection connection;

	/**
	 * SQL Statements
	 */
	protected enum Statements {
		SELECT, INSERT, UPDATE, DELETE, DO, REPLACE, LOAD, HANDLER, CALL, CREATE, ALTER, DROP, TRUNCATE, RENAME
	}

	/**
	 * Create a new database
	 * 
	 * @param prefix
	 *            Table prefix
	 */
	public Database(String prefix) {
		this.PREFIX = prefix;
		this.connected = false;
		this.connection = null;
	}

	/**
	 * Get table prefix
	 * 
	 * @return Prefix
	 */
	public String getTablePrefix() {
		return this.PREFIX;
	}

	abstract boolean initialize();

	public abstract Connection open();

	public abstract void close();

	public abstract Connection getConnection();

	public abstract boolean checkConnection();

	public abstract ResultSet query(String query);

	public abstract PreparedStatement prepare(String query);

	protected Statements getStatement(String query) {
		String trimmedQuery = query.trim();
		if (trimmedQuery.substring(0, 6).equalsIgnoreCase("SELECT"))
			return Statements.SELECT;
		else if (trimmedQuery.substring(0, 6).equalsIgnoreCase("INSERT"))
			return Statements.INSERT;
		else if (trimmedQuery.substring(0, 6).equalsIgnoreCase("UPDATE"))
			return Statements.UPDATE;
		else if (trimmedQuery.substring(0, 6).equalsIgnoreCase("DELETE"))
			return Statements.DELETE;
		else if (trimmedQuery.substring(0, 6).equalsIgnoreCase("CREATE"))
			return Statements.CREATE;
		else if (trimmedQuery.substring(0, 5).equalsIgnoreCase("ALTER"))
			return Statements.ALTER;
		else if (trimmedQuery.substring(0, 4).equalsIgnoreCase("DROP"))
			return Statements.DROP;
		else if (trimmedQuery.substring(0, 8).equalsIgnoreCase("TRUNCATE"))
			return Statements.TRUNCATE;
		else if (trimmedQuery.substring(0, 6).equalsIgnoreCase("RENAME"))
			return Statements.RENAME;
		else if (trimmedQuery.substring(0, 2).equalsIgnoreCase("DO"))
			return Statements.DO;
		else if (trimmedQuery.substring(0, 7).equalsIgnoreCase("REPLACE"))
			return Statements.REPLACE;
		else if (trimmedQuery.substring(0, 4).equalsIgnoreCase("LOAD"))
			return Statements.LOAD;
		else if (trimmedQuery.substring(0, 7).equalsIgnoreCase("HANDLER"))
			return Statements.HANDLER;
		else if (trimmedQuery.substring(0, 4).equalsIgnoreCase("CALL"))
			return Statements.CALL;
		else
			return Statements.SELECT;
	}

	public abstract boolean createTable(String query);

	public abstract boolean checkTable(String table);

	public abstract boolean wipeTable(String table);

	public abstract String getCreateStatement(String table);
}
