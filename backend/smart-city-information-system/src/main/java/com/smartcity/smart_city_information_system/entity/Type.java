package com.smartcity.smart_city_information_system.entity;

import jakarta.persistence.*;

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

    public Type() {
    }

    public Type(String type, String path_file, String description) {
        this.type = type;
        this.path_file = path_file;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", path_file='" + path_file + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
