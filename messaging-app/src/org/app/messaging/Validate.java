package org.app.messaging;

import java.sql.ResultSet;

import org.app.messaging.entity.UserInfo;
import org.app.messaging.utility.Utility;

public class Validate {
	public static UserInfo checkUser(String email, String pass) {
		UserInfo user = null;
		try {

			String query = "select * from t_user_info where f_email=? and f_password=md5(?)";
			ResultSet rs = Utility.getResultSet(query, new String[] { email, pass });
			if (rs.next()) {
				user = new UserInfo();
				user.setExtId(rs.getString("f_ext_id"));
				user.setName(rs.getString("f_name"));
				user.setEmail(rs.getString("f_email"));
				user.setMobile(rs.getString("f_mobile"));
				user.setIsActive(rs.getBoolean("f_is_active"));
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
