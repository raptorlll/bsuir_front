package com.leonov.bsuir.convertors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leonov.bsuir.models.UserJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Vera on 14.01.2018.
 */

public class UserJsonConvertor implements ContertorInterface<UserJson, JSONObject> {
    @Override
    public UserJson convert(JSONObject json) throws JSONException {
        Gson gson = new Gson();
        Type type = new TypeToken<UserJson>() {}.getType();
        UserJson userJson = gson.fromJson(json.toString(), type);

        return userJson;
    }
}
