package com.fydp.sci.grocerything.DataModel;


public class UserSession {

    public UserSession(String session, int userId)
    {
        sessionKey = session;
        this.userId = userId;
    }

    private String sessionKey;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}
