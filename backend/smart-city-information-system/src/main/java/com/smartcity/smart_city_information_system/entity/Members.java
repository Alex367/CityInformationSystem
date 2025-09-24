package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@ToString
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

    @OneToMany(mappedBy = "request_members", cascade = CascadeType.ALL)
    private List<Request> requests;

    public Members() {
    }

    public Members(String pw, String active, String user_id) {
        this.pw = pw;
        this.active = active;
        this.user_id = user_id;
    }

    public void add(City tempCity){
        if(cities == null){
            cities = new ArrayList<>();
        }
        cities.add(tempCity);

        tempCity.setMembers(this);
    }

}
