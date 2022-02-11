package com.sunnyweather.arbitrarydoor.User;

public class User_update {
    private String username;
    private String psd;
    private String sex;
    private String birthday;
    private Boolean isShowBirthday;
    private Boolean isShowSex;
    private Integer code;
    private Object data;
    private String message;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Boolean getShowBirthday() {
        return isShowBirthday;
    }

    public void setShowBirthday(Boolean showBirthday) {
        isShowBirthday = showBirthday;
    }

    public Boolean getShowSex() {
        return isShowSex;
    }

    public void setShowSex(Boolean showSex) {
        isShowSex = showSex;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
