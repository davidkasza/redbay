package com.example.services;

import com.example.dtos.*;
import com.example.entities.SellableItem;
import com.example.entities.User;

public interface MapperService {
    UserDTO convertUserToUserDTO(User user);

    User convertUserDTOToUser(UserDTO userDTO);

    RegisteredUserDTO convertUserToRegisteredUserDTO(User user);

    SellableItem convertSellableItemDTOToSellableItem(SellableItemDTO sellableItemDTO, User user);

    SellableItemDTO convertSellableItemToSellableItemDTO(SellableItem sellableItem);

    SellableItemIDDTO convertSellableItemToSellableItemIDDTO(SellableItem sellableItem);

    BidderBuyerUserDTO convertUserToBidderBuyerUserDTO(User user);

    UserDetailsDTO convertUserToUserDetailsDTO(User user);
}
