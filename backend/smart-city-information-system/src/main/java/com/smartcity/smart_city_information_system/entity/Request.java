package com.smartcity.smart_city_information_system.entity;

import com.smartcity.smart_city_information_system.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
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

    public Request(String request,
                   String response,
                   String type,
                   String path_file,
                   String description,
                   Status status) {
        this.request = request;
        this.response = response;
        this.type = type;
        this.path_file = path_file;
        this.description = description;
        this.status = status;
    }

}
