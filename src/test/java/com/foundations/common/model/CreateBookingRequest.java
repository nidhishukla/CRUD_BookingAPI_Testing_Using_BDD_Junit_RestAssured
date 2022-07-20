package com.foundations.common.model;

import lombok.Data;

@Data
public class CreateBookingRequest {
    public String firstname;
    public String lastname;
    public Integer totalprice;
    public Boolean depositpaid;
    public BookingDates bookingdates;
    public String additionalneeds;
}
