package org.app.messaging.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Utility {
	private static Gson gson = new Gson();

	public static void writeResponse(HttpServletResponse response, Object res) throws IOException {
		String responseString = gson.toJson(res);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(responseString);
		out.flush();
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// loading drivers for mysql
		Class.forName("com.mysql.jdbc.Driver");

		// creating connection with the database
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/messaging", "root", "");
		return con;
	}

	public static ResultSet getResultSet(String query, Object[] params) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		int i = 1;
		for (Object param : params) {
			if (param instanceof String) {
				ps.setString(i++, (String) param);
			} else if (param instanceof Boolean) {
				ps.setBoolean(i++, (Boolean) param);
			}
		}
		return ps.executeQuery();
	}

	public static int persist(String query, Object[] params) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		int i = 1;
		for (Object param : params) {
			if (param instanceof String) {
				ps.setString(i++, (String) param);
			} else if (param instanceof Boolean) {
				ps.setBoolean(i++, (Boolean) param);
			} else if (param instanceof Integer) {
				ps.setInt(i++, (Integer) param);
			}
		}
		return ps.executeUpdate();
	}

	public static JsonObject getRequestJson(HttpServletRequest request) throws IOException {
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(request.getReader());
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		return jsonObject;
	}
}
