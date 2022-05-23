package com.example.backEndProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

//    Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String company;
    private String role;
    private String password;
    private String date_of_birth;

//    Relationship Mapping

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> allPostsByUser;


    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE", name = "is_business_account")
    private boolean isBusinessAccount;


//    Constructors

    public User() {}

    public User(Long id, String name, String company, String role, String password,
                String date_of_birth, ArrayList<Post> allPostsByUser, boolean isBusinessAccount) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.role = role;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.allPostsByUser = allPostsByUser;
        this.isBusinessAccount = isBusinessAccount;
    }

    public User(Long id, String company, String password, boolean isBusinessAccount) {
    }


//    Getters and Setters

    public boolean isBusinessAccount() {
        return isBusinessAccount;
    }

    public void setBusinessAccount(boolean businessAccount) {
        isBusinessAccount = businessAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public List<Post> getAllPostsByUser() {
        return allPostsByUser;
    }

    public void setAllPostsByUser(List<Post> allPostsByUser) {
        this.allPostsByUser = allPostsByUser;
    }

//    Methods

//    Adding this to Post, simply add like to post to begin

    public void likePost(Post post) {
        int newLikeTotal = post.getNumber_of_likes() + 1;
        post.setNumber_of_likes(newLikeTotal);
    }

}
