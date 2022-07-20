package com.foundations.common.model;

import lombok.Data;

@Data
public class CreateBookingResponse {
    /**
     *
     * @author Nidhi SHukla
     */
    public String bookingid;
    public CreateBookingRequest booking;
}
