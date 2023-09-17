package com.example.services;

import com.example.dtos.SellableItemDTO;
import com.example.dtos.SellableItemIDDTO;
import com.example.dtos.SellableItemListDTO;
import com.example.entities.Bid;
import com.example.entities.SellableItem;
import com.example.entities.User;
import com.example.exception.BidTimeExpiredException;
import com.example.exception.InvalidBidPriceException;
import com.example.exception.SellableItemNotFoundException;
import com.example.repositories.SellableItemRepository;
import com.example.security.RedbayUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class SellableItemServiceImpl implements SellableItemService {
    private RedbayUserDetailsService redbayUserDetailsService;

    private MapperService mapperService;

    private UserService userService;

    private SellableItemRepository sellableItemRepository;

    private GlobalUpdateService globalUpdateService;

    @Autowired
    public SellableItemServiceImpl(RedbayUserDetailsService redbayUserDetailsService, MapperService mapperService, UserService userService, SellableItemRepository sellableItemRepository, GlobalUpdateService globalUpdateService) {
        this.redbayUserDetailsService = redbayUserDetailsService;
        this.mapperService = mapperService;
        this.userService = userService;
        this.sellableItemRepository = sellableItemRepository;
        this.globalUpdateService = globalUpdateService;
    }

    public SellableItemDTO addNewItem(SellableItemDTO sellableItemDTO, HttpServletRequest request) {
        User user = redbayUserDetailsService.getUserByUsernameFromRequest(request);
        SellableItem sellableItem = mapperService.convertSellableItemDTOToSellableItem(sellableItemDTO, user);
        user.addSellableItem(sellableItem);

        sellableItemRepository.save(sellableItem);

        sellableItemDTO = mapperService.convertSellableItemToSellableItemDTO(sellableItem);

        return sellableItemDTO;
    }

    public SellableItemListDTO getSellableItems(HttpServletRequest request) {
        User user = redbayUserDetailsService.getUserByUsernameFromRequest(request);
        SellableItemListDTO sellableItemListDTO = new SellableItemListDTO();

        List<SellableItem> list = sellableItemRepository.findAllExceptCurrentUserItems(user.getUsername());

        for (int i = 0; i < list.size(); i++) {
            sellableItemListDTO.addSellableItems(mapperService.convertSellableItemToSellableItemDTO(list.get(i)));
        }

        return sellableItemListDTO;
    }

    public SellableItemIDDTO getSellableItemById(Long id) {
        SellableItem sellableItem = getValidSellableItem(id);
        SellableItemIDDTO sellableItemIDDTO = mapperService.convertSellableItemToSellableItemIDDTO(sellableItem);
        return sellableItemIDDTO;
    }

    private SellableItem getValidSellableItem(Long id) {
        SellableItem sellableItem = sellableItemRepository.findById(id).orElseThrow(() -> new SellableItemNotFoundException());
        globalUpdateService.updateSellableItem(sellableItem);
        return sellableItem;
    }

    public SellableItemIDDTO setBid(HttpServletRequest request, Long id, double bidPrice) {
        User user = redbayUserDetailsService.getUserByUsernameFromRequest(request);
        SellableItem sellableItem = sellableItemRepository.findById(id).orElseThrow(() -> new SellableItemNotFoundException());

        sellableItem = globalUpdateService.updateSellableItem(sellableItem);

        if (!GeneralUtility.isAGivenDateAfterTheCurrentDate(new SimpleDateFormat(sellableItem.getBidEndDate()))) {
            throw new BidTimeExpiredException();
        }

        if (sellableItem.getSold()) {
            throw new BidTimeExpiredException();
        }

        if ((sellableItem.getLastBidPrice() == 0 && bidPrice <= sellableItem.getStartPrice()) || bidPrice <= sellableItem.getLastBidPrice()) {
            throw new InvalidBidPriceException();
        }

        sellableItem.addBid(new Bid(user));
        sellableItem.setLastBidPrice(bidPrice);

        sellableItemRepository.save(sellableItem);

        SellableItemIDDTO sellableItemIDDTO = mapperService.convertSellableItemToSellableItemIDDTO(sellableItem);
        return sellableItemIDDTO;
    }
}
