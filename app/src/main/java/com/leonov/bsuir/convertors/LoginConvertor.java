package com.leonov.bsuir.convertors;

import com.leonov.bsuir.models.Login;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vera on 14.01.2018.
 */

public class LoginConvertor implements ContertorInterface<Login, JSONObject> {
    @Override
    public Login convert(JSONObject json) throws JSONException {
        String login = json.getString("login");
        String password = json.getString("password");
        String jwt = json.getString("jwt");
        String email = json.getString("email");

        Login loginModel = new Login(login, email, password, jwt);
        return loginModel;
    }
}
