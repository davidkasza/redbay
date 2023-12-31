package com.example.dtos;

public class BidderBuyerUserDTO {
    private String username;

    private String name;

    private String email;

    public BidderBuyerUserDTO() {
    }

    public BidderBuyerUserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
