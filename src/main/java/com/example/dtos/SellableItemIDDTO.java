package com.example.dtos;

import java.util.List;

public class SellableItemIDDTO {
    private String sellerName;

    private String name;

    private String description;

    private String photoURL;

    private double buyingPrice;

    private double lastBidPrice;

    private List<BidderBuyerUserDTO> bids;

    private String buyerName;

    private BidderBuyerUserDTO buyerUser;

    public SellableItemIDDTO() {
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

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public List<BidderBuyerUserDTO> getBids() {
        return bids;
    }

    public void setBids(List<BidderBuyerUserDTO> bids) {
        this.bids = bids;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public BidderBuyerUserDTO getBuyerUser() {
        return buyerUser;
    }

    public void setBuyerUser(BidderBuyerUserDTO buyerUser) {
        this.buyerUser = buyerUser;
    }

    public double getLastBidPrice() {
        return lastBidPrice;
    }

    public void setLastBidPrice(double lastBidPrice) {
        this.lastBidPrice = lastBidPrice;
    }
}
