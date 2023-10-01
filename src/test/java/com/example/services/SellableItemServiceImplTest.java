package com.example.services;

import com.example.dtos.SellableItemDTO;
import com.example.dtos.SellableItemIDDTO;
import com.example.dtos.SellableItemListDTO;
import com.example.entities.SellableItem;
import com.example.entities.User;
import com.example.repositories.SellableItemRepository;
import com.example.repositories.UserRepository;
import com.example.security.RedbayUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class SellableItemServiceImplTest {
    RedbayUserDetailsService redbayUserDetailsService;

    MapperService mapperService;

    UserService userService;

    SellableItemServiceImpl sellableItemService;

    GlobalUpdateService globalUpdateService;

    SellableItemRepository sellableItemRepository;

    UserRepository userRepository;

    BeanFactory beanFactory;

    @Autowired
    SellableItemServiceImplTest(BeanFactory beanFactory) {
        redbayUserDetailsService = Mockito.mock(RedbayUserDetailsService.class);
        mapperService = Mockito.mock(MapperService.class);
        userService = Mockito.mock(UserService.class);
        globalUpdateService = Mockito.mock(GlobalUpdateService.class);
        sellableItemRepository = Mockito.mock(SellableItemRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        sellableItemService = new SellableItemServiceImpl(redbayUserDetailsService, mapperService, userService, sellableItemRepository, userRepository, globalUpdateService);
        this.beanFactory = beanFactory;
    }

    @Test
    void addNewItem_ReturnsSellableItemDTO() {
        User fullFakeUser = beanFactory.getBean("fullFakeUserWithIds", User.class);
        SellableItem fakeSellableItem = beanFactory.getBean("fakeSellableItem", SellableItem.class);
        SellableItemDTO fakeSellableItemDTO = beanFactory.getBean("fakeSellableItemDTO", SellableItemDTO.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        Mockito.when(redbayUserDetailsService.getUserByUsernameFromRequest(request)).thenReturn(fullFakeUser);
        Mockito.when(mapperService.convertSellableItemDTOToSellableItem(fakeSellableItemDTO, fullFakeUser)).thenReturn(fakeSellableItem);
        Mockito.when(mapperService.convertSellableItemToSellableItemDTO(fakeSellableItem)).thenReturn(fakeSellableItemDTO);

        SellableItemDTO returnOfAddNewItem = sellableItemService.addNewItem(fakeSellableItemDTO, request);

        Assertions.assertEquals(fakeSellableItemDTO, returnOfAddNewItem);
    }

    @Test
    void getSellableItems_ReturnsSellableItemListDTO() {
        User fullFakeUser = beanFactory.getBean("fullFakeUserWithIds", User.class);
        User anotherFullFakeUser = beanFactory.getBean("anotherFullFakeUserWithIds", User.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        Mockito.when(redbayUserDetailsService.getUserByUsernameFromRequest(request)).thenReturn(fullFakeUser);

        List<SellableItem> mockSellableItems = anotherFullFakeUser.getSellableItems();

        Mockito.when(sellableItemRepository.findAllExceptCurrentUserItems(fullFakeUser.getUsername())).thenReturn(mockSellableItems);


        SellableItemDTO mockSellableItemDTO = beanFactory.getBean("anotherFakeSellableItem", SellableItemDTO.class);
        Mockito.when(mapperService.convertSellableItemToSellableItemDTO(any(SellableItem.class))).thenReturn(mockSellableItemDTO);

        SellableItemListDTO result = sellableItemService.getSellableItems(request);

        Assertions.assertEquals(mockSellableItemDTO, result.getSellableItemDTOList().get(0));
    }

    @Test
    void getSellableItems_ReturnsEmptySellableItemListDTO() {
        User fullFakeUser = beanFactory.getBean("fullFakeUserWithIds", User.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        Mockito.when(redbayUserDetailsService.getUserByUsernameFromRequest(request)).thenReturn(fullFakeUser);

        List<SellableItem> list = new ArrayList<>();
        SellableItemDTO mockSellableItemDTO = new SellableItemDTO();

        Mockito.when(sellableItemRepository.findAllExceptCurrentUserItems(fullFakeUser.getUsername())).thenReturn(list);
        Mockito.when(mapperService.convertSellableItemToSellableItemDTO(any(SellableItem.class))).thenReturn(mockSellableItemDTO);

        SellableItemListDTO result = sellableItemService.getSellableItems(request);

        Assertions.assertEquals(list, result.getSellableItemDTOList());
    }

    @Test
    void getSellableItemByID_ReturnsValidSellableItemIDDTO() {
        Long id = 1L;
        User user = beanFactory.getBean("fullFakeUserWithIds", User.class);
        SellableItem sellableItem = user.getSellableItems().get(0);
        SellableItemIDDTO expectedDTO = mapperService.convertSellableItemToSellableItemIDDTO(beanFactory.getBean("fakeSellableItem", SellableItem.class));

        Mockito.when(sellableItemRepository.findById(id)).thenReturn(Optional.of(sellableItem));
        Mockito.when(mapperService.convertSellableItemToSellableItemIDDTO(sellableItem)).thenReturn(expectedDTO);

        SellableItemIDDTO resultDTO = sellableItemService.getSellableItemById(id);

        Assertions.assertEquals(expectedDTO, resultDTO);
    }
}
