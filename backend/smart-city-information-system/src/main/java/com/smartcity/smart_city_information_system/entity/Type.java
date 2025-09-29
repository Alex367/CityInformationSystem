package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "typeTable")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "path_file")
    private String path_file;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "theType", cascade = CascadeType.ALL)
    private List<Place> places;

    public Type() {
    }

    public Type(String type, String path_file, String description) {
        this.type = type;
        this.path_file = path_file;
        this.description = description;
    }

    public void add(Place tempPlace){
        if(places == null){
            places = new ArrayList<>();
        }
        places.add(tempPlace);

        tempPlace.setTheType(this);
    }

}
