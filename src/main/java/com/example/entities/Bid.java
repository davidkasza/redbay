package com.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private User userBid;

    public Bid() {
    }

    public Bid(User userBidList) {
        this.userBid = userBidList;
    }

    public User getUserBid() {
        return userBid;
    }

    public void setUserBid(User userBidList) {
        this.userBid = userBidList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
