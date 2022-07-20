package com.foundations.common.model;

import lombok.Data;

@Data
public class CreateBookingResponse {
    public String bookingid;
    public CreateBookingRequest booking;
}
