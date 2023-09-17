package com.example.configurations;

import com.example.dtos.SellableItemDTO;
import com.example.dtos.SellableItemListDTO;
import com.example.dtos.UserDTO;
import com.example.entities.SellableItem;
import com.example.entities.User;
import com.example.services.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TestConfiguration {

    private final String fakeUserName = "fakeUser";

    private final String fakeUsersName = "John Doe";

    private final String fakeUserEmail = "fake_user@email.com";

    private final String fakeUserPassword = "password";

    private final MapperService mapperService;

    @Autowired
    public TestConfiguration(MapperService mapperService) {
        this.mapperService = mapperService;
    }


    @Bean(name = "fakeUser")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    User getFakeUser() {
        User fakeUser = new User(fakeUserName, fakeUsersName, fakeUserEmail, fakeUserPassword);
        return fakeUser;
    }

    @Bean(name = "fakeUserWithIds")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    User getFakeUserWithIds() {
        User fakeUser = getFakeUser();
        fakeUser.setId(1L);
        return fakeUser;
    }

    @Bean(name = "fakeUserDTO")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    UserDTO getFakeUserDTO() {
        return mapperService.convertUserToUserDTO(getFakeUser());
    }

    @Bean(name = "fullFakeUser")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    User getFullFakeUser() {
        User user = getFakeUser();
        SellableItem sellableItem = getFakeSellableItem();
        sellableItem.setUser(getFakeUserWithIds());
        return user;
    }

    @Bean(name = "fullFakeUserWithIds")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    User getFullFakeUserWithIds() {
        User user = getFakeUserWithIds();
        SellableItem sellableItem = getFakeSellableItem();
        sellableItem.setId(1L);
        List<SellableItem> sellableItems = new ArrayList<>();
        sellableItems.add(sellableItem);

        user.setSellableItems(sellableItems);
        return user;
    }

    @Bean(name = "anotherFullFakeUserWithIds")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    User getAnotherFullFakeUserWithIds() {
        User user = getFakeUserWithIds();
        user.setUsername("reallyFakeUser");
        user.setId(2L);
        SellableItem sellableItem = getFakeSellableItem();
        sellableItem.setId(2L);
        sellableItem.setUser(user);

        List<SellableItem> sellableItems = new ArrayList<>();
        sellableItems.add(sellableItem);
        user.setSellableItems(sellableItems);
        user.getSellableItems().get(0).setName("iPhone 12");
        return user;
    }

    @Bean(name = "fakeSellableItem")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    SellableItem getFakeSellableItem() {
        SellableItem sellableItem = new SellableItem("Huawei P20 PRO", "mobile", "https://google.com", "08-27-2023 15:37", "08-27-2025 15:37", 200.0);
        return sellableItem;
    }

    @Bean(name = "anotherFakeSellableItem")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    SellableItemDTO getAnotherFakeSellableItem() {
        SellableItemDTO sellableItemDTO = new SellableItemDTO("iPhone 12", "mobile", "https://google.com", "08-27-2023 15:37", "08-27-2025 15:37", 200.0);
        return sellableItemDTO;
    }

    @Bean(name = "fakeSellableItemList")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    List<SellableItem> getFullFakeSellableItem() {
        User fakeUser = getFullFakeUserWithIds();
        SellableItem sellableItem = getFakeSellableItem();
        sellableItem.setId(1L);
        sellableItem.setUser(fakeUser);
        List<SellableItem> sellableItems = fakeUser.getSellableItems();
        return sellableItems;
    }

    @Bean(name = "fakeSellableItemDTO")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    SellableItemDTO getFakeSellableItemDTO() {
        SellableItemDTO sellableItemDTO = new SellableItemDTO("Huawei P20 PRO", "mobile", "https://google.com", "08-27-2023 15:37", "08-27-2025 15:37", 200.0);
        return sellableItemDTO;
    }

    @Bean(name = "fakeSellableItemListDTO")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    SellableItemListDTO getFakeSellableItemListDTO() {
        SellableItemListDTO sellableItemListDTO = new SellableItemListDTO();

        SellableItemDTO sellableItem = getFakeSellableItemDTO();
        SellableItemDTO sellableItem1 = getFakeSellableItemDTO();

        sellableItemListDTO.addSellableItems(sellableItem);
        sellableItemListDTO.addSellableItems(sellableItem1);

        return sellableItemListDTO;
    }
}