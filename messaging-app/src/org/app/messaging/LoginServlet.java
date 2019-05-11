package org.app.messaging;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.app.messaging.entity.Response;
import org.app.messaging.entity.UserInfo;
import org.app.messaging.utility.Utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet(name="LoginServlet", urlPatterns="/login")
public class LoginServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Response<UserInfo> res = new Response<>();
        JsonObject jsonObject = Utility.getRequestJson(request);
        String email = jsonObject.get("email").getAsString();
        String pass = jsonObject.get("password").getAsString();
        UserInfo user = Validate.checkUser(email, pass);
		res.setStatusMessage("SUCCESS");
        if (user == null) {
			res.setStatusMessage("USER_NOT_AVAILABLE");
		}
        res.setStatusCode("200");
        res.setData(user);
        Utility.writeResponse(response, res);
    }
}
