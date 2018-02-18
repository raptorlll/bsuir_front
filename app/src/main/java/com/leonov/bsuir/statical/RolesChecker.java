package com.leonov.bsuir.statical;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leonov.bsuir.enums.RolesEnum;
import com.leonov.bsuir.global.GlobalClass;
import com.leonov.bsuir.models.Role;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class RolesChecker {
    private static final String TAG = "BaseActivity";
    SharedPreferences sp;
    private Set<Role> roles = new HashSet<>();

    private RolesChecker() {
    }

    public static RolesChecker getInstance(){
        return new RolesChecker();
    }

    public boolean isAdmin(){
        Set<Role> roles = getRoles();

        return checkRole(roles, RolesEnum.ADMIN);
    }

    public boolean isCustomer(){
        Set<Role> roles = getRoles();

        return checkRole(roles, RolesEnum.CUSTOMER);
    }

    public boolean isConsultant(){
        Set<Role> roles = getRoles();

        return checkRole(roles, RolesEnum.CONSULTANT);
    }

    public boolean checkRole(Set<Role> roles, RolesEnum rolesEnum){
        for (Role role : roles) {
            if(role.getValue().equals(rolesEnum.getValue())){
                return true;
            }
        }

        return false;
    }

    public boolean isUserLogeddin() {
        sp = GlobalClass.getContext().getSharedPreferences("login", MODE_PRIVATE);
        //if SharedPreferences contains username and password then redirect to Home activity
        return  sp.contains("token");
    }

    public Set<Role> getRoles(){
        if (roles.size() == 0) {
            parserRoles();
        }

        return roles;
    }

    public void parserRoles() {
        SharedPreferences sp = GlobalClass.getContext().getSharedPreferences("login", MODE_PRIVATE);
        String rolesString = sp.getString("roles", "");

        Gson gson = new Gson();
        Type type = new TypeToken<Set<Role>>() {}.getType();
        Set<Role> rolesSet = gson.fromJson(rolesString.length() == 0 ? "[]" : rolesString, type);

        /* If there are no roles yet - set default = guest */
        if(rolesSet==null || rolesSet.size() == 0){
            rolesSet.add(new Role("GUEST", "guest"));
        }

        roles = rolesSet;
    }
}
