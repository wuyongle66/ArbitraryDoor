package com.sunnyweather.arbitrarydoor.User;

import java.util.List;

public class User_login {
    private String psd;
    private String telephone;
    private Integer code;
    private User_login.Data data;
    private String message;


    public String getPsd() {
        return psd;
    }

    public void setPsd(String  psd) {
        this.psd = psd;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String  telephone) {
        this.telephone = telephone;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setData(User_login.Data data) {
        this.data = data;
    }
    public User_login.Data getData() {
        return data;
    }
    public static class Data{
        private String tokenHead;
        private String token;


        public String getTokenHead() {
            return tokenHead;
        }

        public void setTokenHead(String  tokenHead) {
            this.tokenHead = tokenHead;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


    }
}
