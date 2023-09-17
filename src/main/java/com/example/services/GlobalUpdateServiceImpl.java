package com.example.services;

import com.example.entities.SellableItem;
import com.example.repositories.SellableItemRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class GlobalUpdateServiceImpl implements GlobalUpdateService {
    private SellableItemRepository sellableItemRepository;

    public GlobalUpdateServiceImpl(SellableItemRepository sellableItemRepository) {
        this.sellableItemRepository = sellableItemRepository;
    }

    public SellableItem updateSellableItem(SellableItem sellableItem) {
        if (!GeneralUtility.isAGivenDateAfterTheCurrentDate(new SimpleDateFormat(sellableItem.getBidEndDate()))) {
            sellableItem.setSold(true);
            if (sellableItem.getBids().size() > 0) {
                sellableItem.setBuyerUser(sellableItem.getBids().get(sellableItem.getBids().size() - 1).getUserBid());
                sellableItem.setEndPrice(sellableItem.getLastBidPrice());
            }
        }
        sellableItemRepository.save(sellableItem);
        return sellableItem;
    }
}