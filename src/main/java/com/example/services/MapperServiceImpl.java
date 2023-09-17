package com.example.services;

import com.example.dtos.*;
import com.example.entities.SellableItem;
import com.example.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapperServiceImpl implements MapperService {
    private ModelMapper modelMapper;

    @Autowired
    public MapperServiceImpl() {
        modelMapper = new ModelMapper();
    }

    public UserDTO convertUserToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        modelMapper.map(userDTO, user);
        return user;
    }

    public RegisteredUserDTO convertUserToRegisteredUserDTO(User user) {
        return modelMapper.map(user, RegisteredUserDTO.class);
    }

    public SellableItem convertSellableItemDTOToSellableItem(SellableItemDTO sellableItemDTO, User user) {
        SellableItem sellableItem = new SellableItem();

        modelMapper.typeMap(SellableItemDTO.class, SellableItem.class).setPostConverter(context -> {
            context.getDestination().setUser(user);
            return context.getDestination();
        }).map(sellableItemDTO, sellableItem);

        return modelMapper.map(sellableItemDTO, SellableItem.class);
    }

    public SellableItemDTO convertSellableItemToSellableItemDTO(SellableItem sellableItem) {
        return modelMapper.map(sellableItem, SellableItemDTO.class);
    }

    public SellableItemIDDTO convertSellableItemToSellableItemIDDTO(SellableItem sellableItem) {
        SellableItemIDDTO sellableItemIDDTO = new SellableItemIDDTO();
        modelMapper.typeMap(SellableItem.class, SellableItemIDDTO.class).setPostConverter(context -> {
            context.getDestination().setSellerName(sellableItem.getUser().getName());
            context.getDestination().setBuyingPrice(sellableItem.getEndPrice());

            List<BidderBuyerUserDTO> bidDTOs = sellableItem.getBids().stream().map(bid -> convertUserToBidderBuyerUserDTO(bid.getUserBid())).collect(Collectors.toList());
            context.getDestination().setBids(bidDTOs);

            return context.getDestination();
        }).map(sellableItem, sellableItemIDDTO);

        return modelMapper.map(sellableItem, SellableItemIDDTO.class);
    }

    public BidderBuyerUserDTO convertUserToBidderBuyerUserDTO(User user) {
        return modelMapper.map(user, BidderBuyerUserDTO.class);
    }
}
