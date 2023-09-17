package com.example.dtos;

import java.util.ArrayList;
import java.util.List;

public class SellableItemListDTO {
    private List<SellableItemDTO> sellableItemDTOList = new ArrayList<>();

    public SellableItemListDTO() {
    }

    public void addSellableItems(SellableItemDTO sellableItemDTO) {
        this.sellableItemDTOList.add(sellableItemDTO);
    }

    public List<SellableItemDTO> getSellableItemDTOList() {
        return sellableItemDTOList;
    }

    public void setSellableItemDTOList(List<SellableItemDTO> sellableItemDTOList) {
        this.sellableItemDTOList = sellableItemDTOList;
    }
}
