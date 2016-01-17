package com.sohos.totocafemobile.pojo;

/**
 * Created by okano on 22.11.2015.
 */
public class UserDetailsTable {

    String name,surname,email,password,birthDate;
    double gender;

    public UserDetailsTable(String name, String surname, String email,
                            String password, String birthDate, double gender) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public UserDetailsTable() {
        super();
        this.name = null;
        this.surname = null;
        this.email = null;
        this.password = null;
        this.birthDate = null;
        this.gender = 0;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
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

    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public double getGender(){
        return gender;
    }
    public void setGender(Double gender) {
        this.gender = gender;
    }




}