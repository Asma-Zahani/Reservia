package com.reservia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookingRequest {
    private String startDate;
    private String endDate;
    private List<Integer> roomIds;
    private Integer totalPrice;
    //private List<String> extras;   // ids ou noms des extras sélectionnés
}
