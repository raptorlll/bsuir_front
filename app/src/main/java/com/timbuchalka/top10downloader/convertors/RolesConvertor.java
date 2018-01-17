package com.timbuchalka.top10downloader.convertors;

import com.timbuchalka.top10downloader.models.Role;
import com.timbuchalka.top10downloader.models.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vera on 14.01.2018.
 */

public class RolesConvertor implements ContertorInterface<Set<Role>, JSONObject> {
    @Override
    public Set<Role> convert(JSONObject json) throws JSONException {
        Set<Role> roles = new HashSet<Role>();

//        String access_token = json.getString("access_token");
//        String token_type = json.getString("token_type");
//        String expires_in = json.getString("expires_in");
//        String scope = json.getString("scope");
//        String jti = json.getString("jti");

        return roles;
    }
}
