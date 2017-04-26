package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.security.SecureRandom;
import java.util.Date;

@Table
public class User extends SugarRecord{
    private Long id;
    private String realname;
    private String password;
    private String email;
    private String gender;
    private String age;
    private String phonenumber;


    private String calorieCount;
    private long passwordExpirationTime;

    public User() {

    }

    public Long getId(){
        return id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public long getPasswordExpirationTime() {
        return passwordExpirationTime;
    }

    public void setPasswordExpirationTime(long passwordExpirationTime) {
        this.passwordExpirationTime = passwordExpirationTime;
    }

    public String getCalorieCount() {
        return calorieCount;
    }

    public void setCalorieCount(String calorieCount) {
        this.calorieCount = calorieCount;
    }
    public String resetPassword(int len) {
        setPasswordExpirationTime(new Date().getTime() + (long)60 * 60 * 1000);

        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(random.nextInt(AB.length())));
        }
        String password = sb.toString();
        setPassword(password);
        this.save();

        return password;
    }
}
