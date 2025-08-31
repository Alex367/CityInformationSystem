package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "new_city")
public class City {
    @Id
    @Column(name = "city")
    private String city;
    @Column(name = "path_file")
    private String path_file;
    @Column(name = "description")
    private String description;

    public City() {
    }

    public City(String city, String path_file, String description) {
        this.city = city;
        this.path_file = path_file;
        this.description = description;
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

    @Override
    public String toString() {
        return "City{" +
                ", city='" + city + '\'' +
                ", path_file='" + path_file + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
