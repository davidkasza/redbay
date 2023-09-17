package com.example.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SellableItemDTO {
    private String name;

    private String description;

    private String photoURL;

    private String bidStartDate;

    private String bidEndDate;

    private double startPrice;

    private double endPrice;

    public SellableItemDTO() {
        this.bidStartDate = new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date());
    }

    public SellableItemDTO(String name, String description, String photoURL, String bidStartDate, String bidEndDate, double startPrice) {
        this.name = name;
        this.description = description;
        this.photoURL = photoURL;
        this.bidStartDate = bidStartDate;
        this.bidEndDate = bidEndDate;
        this.startPrice = startPrice;
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
}
