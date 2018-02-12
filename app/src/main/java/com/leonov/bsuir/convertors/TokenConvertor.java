package com.leonov.bsuir.convertors;

import com.leonov.bsuir.models.Token;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vera on 14.01.2018.
 */

public class TokenConvertor implements ContertorInterface<Token, JSONObject> {
    @Override
    public Token convert(JSONObject json) throws JSONException {
        String access_token = json.getString("access_token");
        String token_type = json.getString("token_type");
        String expires_in = json.getString("expires_in");
        String scope = json.getString("scope");
        String jti = json.getString("jti");

        return new Token(access_token, token_type, expires_in, scope, jti);
    }
}
