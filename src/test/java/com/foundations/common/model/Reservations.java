package com.foundations.common.model;

import lombok.Data;

@Data
public class Reservations {
    private volatile AuthResponse authResponse;
    private volatile CreateBookingResponse createBookingResponse;
}
