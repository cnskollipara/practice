package org.app.messaging;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.app.messaging.entity.MessageInfo;
import org.app.messaging.entity.Response;
import org.app.messaging.entity.UserMessages;
import org.app.messaging.utility.Utility;

import com.google.gson.Gson;

@WebServlet(name="MessagingServlet", urlPatterns="/message")
public class MessagingServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Response<String> res = new Response<>();
		Gson gson = new Gson();
		MessageInfo msgInfo = gson.fromJson(request.getReader(), MessageInfo.class);
		try {
			Validate.validateUpsertMessageRequest(msgInfo);
			// Add/Update Message
			int cnt = MessagingService.addOrUpdate(msgInfo);
			if (cnt > 0) {
				res.setStatusCode("200");
				res.setStatusMessage("SUCCESS");
			} else {
				res.setStatusCode("500");
				res.setStatusMessage("UNABLE_TO_ADD");
			}
		} catch (MessagingException e) {
			res.setStatusMessage(e.getErrorMsg());
			res.setStatusCode("500");
		} catch (Exception e) {
			res.setStatusMessage(e.getMessage());
			res.setStatusCode("500");
		}
		Utility.writeResponse(response, res);
	}
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Response<UserMessages> res = new Response<>();
		try {
			String extId = request.getParameter("extId");
			String searchStr = request.getParameter("searchStr");
			Validate.validateFotExtId(extId);
			// Add/Update Message
			UserMessages userMessages = MessagingService.getUserMessage(extId, searchStr);
			res.setStatusCode("200");
			res.setData(userMessages);
		} catch (MessagingException e) {
			res.setStatusMessage(e.getErrorMsg());
			res.setStatusCode("500");
		} catch (Exception e) {
			res.setStatusMessage(e.getMessage());
			res.setStatusCode("500");
		}
		Utility.writeResponse(response, res);
	}
}
