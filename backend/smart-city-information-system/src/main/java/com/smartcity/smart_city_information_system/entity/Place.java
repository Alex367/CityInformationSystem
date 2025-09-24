package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "place")
    private String place;

    @Column(name = "path_file")
    private String path_file;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_city") // column in this table
    private City theCity;

    @ManyToOne
    @JoinColumn(name = "id_type")
    private Type theType;

    public Place() {
    }

    public Place(String place, String path_file, String description) {
        this.place = place;
        this.path_file = path_file;
        this.description = description;
    }

}
