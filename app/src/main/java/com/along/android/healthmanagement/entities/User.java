package com.along.android.healthmanagement.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class User extends SugarRecord{
    private Long id;
    private String username, password, email, realname, gender, age, phonenumber, weight, height;

    public Long getId(){
        return id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    /*public boolean isValid(User user, Context context) {
        Boolean bool = true;
        if (user.getUsername().isEmpty()) {
            Toast.makeText(context, "username cannot be empty", Toast.LENGTH_LONG).show();
            bool = false;

        } else if (user.getPassword().isEmpty()) {
            Toast.makeText(context, "password cannot be empty", Toast.LENGTH_LONG).show();
            bool = false;
        }

        return bool;
    }*/
}
