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
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "city")
    private String city;
    @Column(name = "path_file")
    private String path_file;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Members members;

    @OneToMany(mappedBy = "theCity", cascade = CascadeType.ALL)
    private List<Place> places;

    public City() {
    }

    public City(String city, String path_file, String description) {
        this.city = city;
        this.path_file = path_file;
        this.description = description;
    }

    public void add(Place tempPlace){
        if(places == null){
            places = new ArrayList<>();
        }
        places.add(tempPlace);

        tempPlace.setTheCity(this);
    }

}
