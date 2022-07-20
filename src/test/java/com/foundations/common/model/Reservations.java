package com.foundations.common.model;

import lombok.Data;

import java.util.List;

@Data
public class Reservations {
    /**
     *
     * @author Nidhi SHukla
     */
    private volatile AuthResponse authResponse;
    private volatile CreateBookingResponse createBookingResponse;
    private volatile List<GetBookingResponse> getBookingResponse;
}
