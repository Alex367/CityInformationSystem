package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String role;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Members member;

    public Roles() {
    }

    public Roles(String role, Members member) {
        this.role = role;
        this.member = member;
    }

}
