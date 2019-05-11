package org.app.messaging.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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

	public static ResultSet getResultSet(String query, String[] params) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		int i = 1;
		for (String param : params) {
			ps.setString(i++, param);
		}
		return ps.executeQuery();
	}
}
