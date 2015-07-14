package me.clip.ezblocks.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

/**
 * MySQL
 * 
 * MySQL Database.
 * @author Maximvdw
 */
public class MySQL extends Database {
	private String hostname = "localhost"; // MySQL Hostname
	private String portnmbr = "3306"; // MySQL port
	private String username = "minecraft"; // MySQL username
	private String password = ""; // MySQL password
	private String database = "minecraft"; // MySQL database

	public MySQL(String prefix, String hostname, String portnmbr,
			String database, String username, String password) {
		super(prefix);
		this.hostname = hostname;
		this.portnmbr = portnmbr;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	protected boolean initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Check that server's Java
													// has MySQL support.
			return true;
		} catch (ClassNotFoundException e) {
			Bukkit.getLogger().severe(
					"[EZBlocks] " + "Class Not Found Exception: "
							+ e.getMessage() + ".");
			return false;
		}
	}

	public Connection open() {
		this.open(true);
		return null;
	}

	public Connection open(boolean showError) {
		if (initialize()) {
			String url = "";
			try {
				url = "jdbc:mysql://" + this.hostname + ":" + this.portnmbr
						+ "/" + this.database + "?allowReconnect=true";
				this.connection = DriverManager.getConnection(url,
						this.username, this.password);
				if (checkConnection())
					connected = true;
				return this.connection;
			} catch (SQLException e) {
				if (showError) {
					Bukkit.getLogger().severe("[EZBlocks] " + url);
					Bukkit.getLogger()
							.severe("[EZBlocks] "
									+ "Could not be resolved because of an SQL Exception: "
									+ e.getMessage() + ".");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void close() {
		// Connection connection = open();
		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			Bukkit.getLogger().severe(
					"[EZBlocks] " + "Failed to close database connection: "
							+ e.getMessage());
		}
	}

	public Connection getConnection() {
		// Create connection if closed or null
		if (this.connection == null)
			return open();
		return this.connection;
	}

	public boolean checkConnection() {
		if (connection != null)
			return true;
		return false;
	}

	public ResultSet query(String query) {
		// Connection connection = null;
		Statement statement = null;
		ResultSet result = null/* new JdbcRowSetImpl() */;
		try {
			// connection = open();
			// if (checkConnection())
			for (int counter = 0; counter < 5 && result == null; counter++) {
				try {
					statement = this.connection.createStatement();
					result = statement.executeQuery("SELECT CURTIME()");
				} catch (SQLException e) {
					if (counter == 4) {
						throw e;
					} else {
						if (e.getMessage().contains("connection closed")) {
							Bukkit.getLogger()
									.severe("[EZBlocks] "
											+ "Error in SQL query. Attempting to reestablish connection. Attempt #"
											+ Integer.toString(counter + 1)
											+ "!");
							this.open(false);
						} else {
							throw e;
						}
					}
				}
			}

			switch (getStatement(query)) {
			case SELECT:
				result = statement.executeQuery(query);
				break;

			default:
				statement.executeUpdate(query);
			}
			return result;
		} catch (SQLException e) {
			Bukkit.getLogger().severe(
					"[EZBlocks] " + "Error in SQL query: " + e.getMessage());
		}
		return result;
	}

	public PreparedStatement prepare(String query) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(query);
			return ps;
		} catch (SQLException e) {
			if (!e.toString().contains("not return ResultSet"))
				Bukkit.getLogger().severe(
						"[EZBlocks] " + "Error in SQL prepare() query: "
								+ e.getMessage());
		}
		return ps;
	}

	public boolean createTable(String query) {
		Statement statement = null;
		try {
			if (query.equals("") || query == null) {
				Bukkit.getLogger().severe(
						"[EZBlocks] " + "SQL query empty: createTable(" + query
								+ ")");
				return false;
			}

			statement = connection.createStatement();
			statement.execute(query);
			return true;
		} catch (SQLException e) {
			Bukkit.getLogger().severe("[EZBlocks] " + e.getMessage());
			return false;
		} catch (Exception e) {
			Bukkit.getLogger().severe("[EZBlocks] " + e.getMessage());
			return false;
		}
	}

	public boolean checkTable(String table) {
		try {
			Statement statement = getConnection().createStatement();

			ResultSet result = statement.executeQuery("SELECT * FROM " + table);

			if (result == null)
				return false;
			if (result != null)
				return true;
		} catch (SQLException e) {
			if (e.getMessage().contains("exist")) {
				return false;
			} else {
				Bukkit.getLogger()
						.severe("[EZBlocks] " + "Error in SQL query: "
								+ e.getMessage());
			}
		}

		if (query("SELECT * FROM " + table) == null)
			return true;
		return false;
	}

	public boolean wipeTable(String table) {
		Statement statement = null;
		String query = null;
		try {
			if (!this.checkTable(table)) {
				Bukkit.getLogger().severe(
						"[EZBlocks] " + "Error wiping table: \"" + table
								+ "\" does not exist.");
				return false;
			}
			statement = getConnection().createStatement();
			query = "DELETE FROM " + table + ";";
			statement.executeUpdate(query);

			return true;
		} catch (SQLException e) {
			if (!e.toString().contains("not return ResultSet"))
				return false;
		}
		return false;
	}

	@Override
	public String getCreateStatement(String table) {
		if (checkTable(table)) {
			try {
				ResultSet result = query("SHOW CREATE TABLE " + table);
				result.next();
				return result.getString(2);
			} catch (Exception ex) {

			}
		}
		return "";
	}
}