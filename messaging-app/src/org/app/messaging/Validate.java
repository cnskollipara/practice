package org.app.messaging;

import java.sql.ResultSet;

import org.app.messaging.entity.MessageInfo;
import org.app.messaging.entity.UserInfo;
import org.app.messaging.utility.Utility;

import com.mysql.jdbc.StringUtils;

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

	public static void validateUpsertMessageRequest(MessageInfo msgInfo) throws MessagingException {
		String errorMsg = null;
		if (msgInfo == null || StringUtils.isNullOrEmpty(msgInfo.getInfo())) {
			errorMsg = "MESSAGE_EMPTY";
		} else if (StringUtils.isNullOrEmpty(msgInfo.getMsgOwner())) {
			errorMsg = "USER_INFO_NOT_AVAILABLE";
		}

		if (!StringUtils.isNullOrEmpty(errorMsg)) {
			throw new MessagingException(errorMsg);
		}
	}

	public static void validateFotExtId(String extId) throws MessagingException {
		String errorMsg = null;
		if (StringUtils.isNullOrEmpty(extId)) {
			throw new MessagingException("USER_INFO_NOT_AVAILABLE");
		}
	}
}
