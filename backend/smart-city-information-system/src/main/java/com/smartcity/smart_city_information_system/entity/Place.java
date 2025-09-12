package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public City getTheCity() {
        return theCity;
    }

    public void setTheCity(City theCity) {
        this.theCity = theCity;
    }

    public Type getTheType() {
        return theType;
    }

    public void setTheType(Type theType) {
        this.theType = theType;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", path_file='" + path_file + '\'' +
                ", description='" + description + '\'' +
                ", theCity=" + theCity +
                ", theType=" + theType +
                '}';
    }
}
