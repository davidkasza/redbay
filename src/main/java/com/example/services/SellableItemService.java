package com.example.services;

import com.example.dtos.SellableItemDTO;
import com.example.dtos.SellableItemIDDTO;
import com.example.dtos.SellableItemListDTO;

import javax.servlet.http.HttpServletRequest;

public interface SellableItemService {
    SellableItemDTO addNewItem(SellableItemDTO sellableItemDTO, HttpServletRequest request);

    SellableItemListDTO getSellableItems(HttpServletRequest request);

    SellableItemIDDTO getSellableItemById(Long id);

    SellableItemIDDTO setBid(HttpServletRequest request, Long id, double bidPrice);
}
