package com.cyt.imoocmusic.helps;

public class UserHelper {

    private static UserHelper instance;

    private UserHelper(){}

    public static UserHelper getInstance(){
        if (instance == null){
            synchronized (UserHelper.class){
                if (instance == null){
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
