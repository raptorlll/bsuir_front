package com.timbuchalka.top10downloader.models;

/**
 * Created by Vera on 14.01.2018.
 */

public class Login {
    private String login;
    private String password;
    private String jwt;
    private String email;

    public Login() {
    }

    public Login(String login, String password, String jwt) {
        super();
        this.login = login;
        this.password = password;
        this.jwt = jwt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public Login(String login, String email, String password, String jwt) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.jwt = jwt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwt() {
        return jwt;
    }

    @Override
    public String toString() {
        return "Login{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", jwt='" + jwt + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
