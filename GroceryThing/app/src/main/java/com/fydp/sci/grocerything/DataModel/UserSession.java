package com.fydp.sci.grocerything.DataModel;


public class UserSession {

    public UserSession(String session)
    {
        sessionKey = session;
    }

    private String sessionKey;
    private String username;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
