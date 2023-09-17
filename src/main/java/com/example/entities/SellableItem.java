package com.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sellable_items")
public class SellableItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String name;

    private String description;

    private String photoURL;

    private String bidStartDate;

    private String bidEndDate;

    private double startPrice;

    private double endPrice;

    private double lastBidPrice;

    private boolean sold;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private User user;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<Bid> bids;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private User buyerUser;

    public SellableItem() {
        this.sold = false;
    }

    public SellableItem(String name, String description, String photoURL, String bidStartDate, String bidEndDate, double startPrice) {
        this.name = name;
        this.description = description;
        this.photoURL = photoURL;
        this.bidStartDate = bidStartDate;
        this.bidEndDate = bidEndDate;
        this.startPrice = startPrice;
        this.sold = false;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getBidStartDate() {
        return bidStartDate;
    }

    public void setBidStartDate(String bidStartDate) {
        this.bidStartDate = bidStartDate;
    }

    public String getBidEndDate() {
        return bidEndDate;
    }

    public void setBidEndDate(String bidEndDate) {
        this.bidEndDate = bidEndDate;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public double getLastBidPrice() {
        return lastBidPrice;
    }

    public void setLastBidPrice(double lastBidPrice) {
        this.lastBidPrice = lastBidPrice;
    }

    public User getBuyerUser() {
        return buyerUser;
    }

    public void setBuyerUser(User buyerUser) {
        this.buyerUser = buyerUser;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }
}
