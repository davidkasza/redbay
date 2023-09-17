package com.example.controllers;

import com.example.dtos.BidDTO;
import com.example.dtos.ErrorDTO;
import com.example.dtos.SellableItemDTO;
import com.example.dtos.SellableItemListDTO;
import com.example.services.GeneralUtility;
import com.example.services.SellableItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/sellable-item")
public class SellableItemController {
    private SellableItemService sellableItemService;

    public SellableItemController(SellableItemService sellableItemService) {
        this.sellableItemService = sellableItemService;
    }

    @PostMapping("/add-new-item")
    public ResponseEntity<?> addNewItem(@RequestBody SellableItemDTO sellableItemDTO, HttpServletRequest request) {
        String parameter = "";

        if (GeneralUtility.isEmptyOrNull(sellableItemDTO.getName())) {
            parameter = "Name of the item";
        } else if (GeneralUtility.isEmptyOrNull(sellableItemDTO.getPhotoURL())) {
            parameter = "Photo URL";
        } else if (GeneralUtility.isEmptyOrNull(sellableItemDTO.getBidEndDate())) {
            parameter = "End date";
        } else if (GeneralUtility.isEmptyOrNull(String.valueOf(sellableItemDTO.getStartPrice()))) {
            parameter = "The price it starts from";
        }

        if (parameter.length() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", parameter + " is required!"));
        }

        if (!GeneralUtility.isAGivenDateAfterTheCurrentDate(new SimpleDateFormat(sellableItemDTO.getBidEndDate()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", "The given end date of the bid can not be equal or before the current date!"));
        }

        if (!GeneralUtility.isDateValid(sellableItemDTO.getBidEndDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", "The given time format is not valid!"));
        }

        if (!GeneralUtility.isURLValid(sellableItemDTO.getPhotoURL())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", "The given URL format is not valid!"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(sellableItemService.addNewItem(sellableItemDTO, request));
    }

    @GetMapping("/all")
    public ResponseEntity<SellableItemListDTO> getAllItemsForOtherUsers(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(sellableItemService.getSellableItems(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(HttpServletRequest request, @PathVariable String id) {
        if (!GeneralUtility.isValidLongNumber(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", "The given id is not a valid number!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(sellableItemService.getSellableItemById(Long.parseLong(id)));
    }

    @PatchMapping("/bid/{id}")
    public ResponseEntity<?> bidItemById(HttpServletRequest request, @PathVariable String id, @RequestBody BidDTO bidDTO) {
        if (!GeneralUtility.isValidLongNumber(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", "The given id is not a valid number!"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(sellableItemService.setBid(request, Long.parseLong(id), bidDTO.getBidPrice()));
    }
}
