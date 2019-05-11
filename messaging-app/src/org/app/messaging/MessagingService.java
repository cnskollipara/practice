package org.app.messaging;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.app.messaging.entity.MessageInfo;
import org.app.messaging.entity.UserMessages;
import org.app.messaging.utility.Utility;

public class MessagingService {

	public static int addOrUpdate(MessageInfo msgInfo) throws MessagingException {
		try {
			boolean isNew = (msgInfo.getId() == null || msgInfo.getId() == 0);
			String query = null;
			Object[] params = null;
			if (isNew) {
				query = "INSERT INTO t_message_info(f_info, f_msg_owner, f_is_public, f_agent) values (?, ?, ?, ?)";
				params = new Object[] { msgInfo.getInfo(), msgInfo.getMsgOwner(), msgInfo.getIsPublic(),
						msgInfo.getMsgOwner() };
			} else {
				query = "UPDATE t_message_info " + "SET " + "f_info = COALESCE(?, f_info), "
						+ "f_is_public = COALESCE(?, f_is_public) WHERE f_id = ?";
				params = new Object[] { msgInfo.getInfo(), msgInfo.getIsPublic(), msgInfo.getId() };
			}
			int cnt = Utility.persist(query, params);
			return cnt;
		} catch (Exception e) {
			throw new MessagingException(e.getMessage());
		}
	}

	public static UserMessages getUserMessage(String extId, String searchStr) throws MessagingException {
		UserMessages userMessages = new UserMessages();
		List<MessageInfo> messages = new ArrayList<>();
		userMessages.setExtId(extId);
		String query = "SELECT * FROM t_message_info WHERE (f_msg_owner = ? OR f_is_public = 1) AND (? IS NULL OR f_info LIKE ?)";
		Object[] params = new Object[] { extId, searchStr, "%"+searchStr+"%" };
		try {
			ResultSet rs = Utility.getResultSet(query, params);
			while(rs.next()) {
				MessageInfo info = new MessageInfo();
				info.setInfo(rs.getString("f_info"));
				info.setMsgOwner(rs.getString("f_msg_owner"));
				info.setIsPublic(rs.getBoolean("f_is_public"));
				info.setCreatedTS(rs.getTimestamp("f_created_ts"));
				info.setUpdatedTS(rs.getTimestamp("f_updated_ts"));
				info.setAgent(rs.getString("f_agent"));
				info.setComment(rs.getString("f_comment"));
				messages.add(info);
			}
			userMessages.setMessages(messages);
		} catch (Exception e) {
			throw new MessagingException(e.getMessage());
		}
		return userMessages;
	}

	public static int deleteUserMessage(String extId, String msgId) throws MessagingException {
		try {
			String query = "DELETE FROM t_message_info WHERE f_msg_owner = ? AND f_id = ?";
			Object[] params = new Object[] { extId, Integer.parseInt(msgId) };
			int cnt = Utility.persist(query, params);
			return cnt;
		} catch (Exception e) {
			throw new MessagingException(e.getMessage());
		}
	}

}
