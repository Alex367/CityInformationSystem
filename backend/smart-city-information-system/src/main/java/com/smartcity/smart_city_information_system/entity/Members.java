package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Members {

    @Id
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "pw")
    private String pw;

    @Column(name = "active")
    private String active;

    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private List<City> cities;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Roles role;

    public Members() {
    }

    public Members(String pw, String active, String user_id) {
        this.pw = pw;
        this.active = active;
        this.user_id = user_id;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void add(City tempCity){
        if(cities == null){
            cities = new ArrayList<>();
        }
        cities.add(tempCity);

        tempCity.setMembers(this);
    }

    @Override
    public String toString() {
        return "Members{" +
                "user_id='" + user_id + '\'' +
                ", pw='" + pw + '\'' +
                ", active='" + active + '\'' +
                ", cities=" + cities +
                ", role=" + role +
                '}';
    }
}
