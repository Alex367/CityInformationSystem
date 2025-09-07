package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPath_file() {
        return path_file;
    }

    public void setPath_file(String path_file) {
        this.path_file = path_file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void add(Place tempPlace){
        if(places == null){
            places = new ArrayList<>();
        }
        places.add(tempPlace);

        tempPlace.setTheCity(this);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", path_file='" + path_file + '\'' +
                ", description='" + description + '\'' +
                ", members=" + members +
                ", places=" + places +
                '}';
    }
}
