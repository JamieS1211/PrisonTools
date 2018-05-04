package com.github.jamies1211.prisontools.ActionsConfig;

import com.github.jamies1211.prisontools.PrisonTools;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.sql.SqlService;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.github.jamies1211.prisontools.PrisonTools.plugin;

/**
 * Created by Jamie on 04/01/2017.
 */
public class MysqlActions {
	private static SqlService sql;

	public static String jdbcURL = "jdbc:mysql://%user%:%password%@%host%/%db%";
	public static String prisontoolsUser;
	public static String prisontoolsPassword;
	public static String prisontoolsHost;
	public static String prisontoolsDb;
	public static String prisontoolsTable;

	static Connection conn = null;

	public static javax.sql.DataSource getDataSource(String jdbcUrl) throws SQLException {
		if (sql == null) {
			sql = Sponge.getServiceManager().provide(SqlService.class).get();
		}
		return sql.getDataSource(jdbcUrl);
	}

	public static void startConnection() {
		try {
			conn = getDataSource(
					jdbcURL.replace("%user%", prisontoolsUser).replace("%password%", prisontoolsPassword).replace("%host%", prisontoolsHost).replace("/%db%", "")
			).getConnection();

			createDatabaseIfRequired();
			conn.close();
			openConnectionIfRequired();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createDatabaseIfRequired () throws SQLException {
		String query = "CREATE DATABASE IF NOT EXISTS $db".replace("$db", prisontoolsDb);

		conn.prepareStatement(query).executeQuery();
	}

	public static void createTablesIfRequired () throws SQLException {

		String query = "CREATE TABLE IF NOT EXISTS %table%(" +

				"UUID VARCHAR(36)," +
				"safariData TEXT NULL," +
				"evTrainData TEXT NULL," +
				"gymData TEXT NULL," +
				"eventData TEXT NULL," +
				"usernameData TEXT NULL," +

				"PRIMARY KEY (UUID))ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci";

		conn.prepareStatement(query.replace("%table%", prisontoolsTable)).executeQuery();
	}

	public static void openConnectionIfRequired () {
		jdbcURL = jdbcURL.replace("%user%", prisontoolsUser).replace("%password%", prisontoolsPassword).replace("%host%", prisontoolsHost).replace("%db%", prisontoolsDb);
		try {

			if (conn == null || conn.isClosed()) {
				closeConnectionIfRequired();
				conn = getDataSource(jdbcURL).getConnection();
				createTablesIfRequired();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnectionIfRequired () {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static HashMap<Integer, HashMap<String, Object>> mysqlQueries(String query) {

		openConnectionIfRequired();

		HashMap<Integer, HashMap<String, Object>> reply = new HashMap<>();

		// If no query return empty.
		if (query.equals("")) {
			return reply;
		} else {
			query = query.replace("%table%", prisontoolsTable);
		}

		try {
			if (conn != null && !conn.isClosed()) {
				ResultSet result = conn.prepareStatement(query).executeQuery();

				// Get all data in request storing in rows and columns to hashmap.
				int row = 1;
				while (result.next()) {
					HashMap<String, Object> values = new HashMap<>();

					for (int column = 1; column <= result.getMetaData().getColumnCount(); column++) {
						String columnName = result.getMetaData().getColumnName(column);
						Object value = result.getObject(column);
						values.put(columnName, value);
					}
					reply.put(row, values);

					row++;
				}
			} else {
				MessageChannel.TO_CONSOLE.send(TextSerializers.FORMATTING_CODE.deserialize("ERROR: Mysql connection is closed"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reply;
	}

	public static void mysqlUpdates(String query) {
		openConnectionIfRequired();

		// If no query return empty.
		if (query.equals("")) {
			return;
		} else {
			query = query.replace("%table%", prisontoolsTable);
		}

		try {
			conn.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static HashMap<String, Object> playerJSONRawGetting(String playerUUIDString, String column) {

		playerExistsInDatabase(playerUUIDString);

		String query = "SELECT %column% FROM %table% where UUID like \"%playerUUIDString%\""
				.replace("%playerUUIDString%", playerUUIDString)
				.replace("%column%", column);

		return mysqlQueries(query).get(1);
	}

	public static JsonObject playerJSONMapGetting(String playerUUIDString, String column) {

		playerExistsInDatabase(playerUUIDString);

		String query = "SELECT %column% FROM %table% where UUID like \"%playerUUIDString%\""
				.replace("%playerUUIDString%", playerUUIDString)
				.replace("%column%", column);

		Object jsonData = mysqlQueries(query).get(1).get(column);
		JsonElement jsonElement = new JsonParser().parse(jsonData.toString());

		if (jsonElement instanceof JsonObject) {
			return (JsonObject) jsonElement;
		}

		return null;
	}

	public static JsonElement playerJSONValueGetting(String playerUUIDString, String column, String desiredValue) {

		JsonObject json = playerJSONMapGetting(playerUUIDString, column);

		if (json != null) {
			return json.get(desiredValue);
		}

		return null;
	}

	public static void playerJsonSetting(String playerUUIDString, String column, String json) {

		playerExistsInDatabase(playerUUIDString);

		String query = "UPDATE %table% SET %column%='%json%' where UUID like \"%playerUUIDString%\""
				.replace("%playerUUIDString%", playerUUIDString)
				.replace("%column%", column)
				.replace("%json%", json);

		mysqlUpdates(query);
	}

	public static void playerDataCreation (String playerUUIDString) {
		String query = "INSERT INTO %table% (UUID) VALUES (\"%playerUUIDString%\")"
				.replace("%playerUUIDString%", playerUUIDString);

		mysqlUpdates(query);
	}

	public static Boolean doesEntryExist (String configPlayerUUID) {

		String query = "SELECT * FROM %table% where UUID like \"%playerUUIDString%\""
				.replace("%playerUUIDString%", configPlayerUUID);

		return !mysqlQueries(query).isEmpty();
	}

	// Make sure player exists in database
	public static void playerExistsInDatabase (String configPlayerUUID) {
		Boolean doesEntryExist = MysqlActions.doesEntryExist(configPlayerUUID);
		if (!doesEntryExist) {
			MysqlActions.playerDataCreation(configPlayerUUID);
		}
	}
}