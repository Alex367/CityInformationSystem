package com.smartcity.smart_city_information_system.entity;

import com.smartcity.smart_city_information_system.enums.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "requestTable")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "request")
    private String request;

    @Column(name = "response")
    private String response;

    @Column(name = "type")
    private String type;

    @Column(name = "path_file")
    private String path_file;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Members request_members;

    public Request() {
    }

    public Request(String request, String response, String type, String path_file, String description, Status status) {
        this.request = request;
        this.response = response;
        this.type = type;
        this.path_file = path_file;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Members getRequest_members() {
        return request_members;
    }

    public void setRequest_members(Members request_members) {
        this.request_members = request_members;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", request='" + request + '\'' +
                ", response='" + response + '\'' +
                ", type='" + type + '\'' +
                ", path_file='" + path_file + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
