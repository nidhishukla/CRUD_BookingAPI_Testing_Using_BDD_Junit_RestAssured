package com.foundations.reservation.service;

import com.foundations.common.AbstractBaseService;
import com.foundations.common.config.Configuration;
import com.foundations.common.config.TestConstants;
import com.foundations.common.model.AuthRequest;
import com.foundations.common.model.AuthResponse;
import com.foundations.common.model.CreateBookingRequest;
import com.foundations.common.model.CreateBookingResponse;
import com.foundations.common.model.Reservations;
import org.apache.http.HttpStatus;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationService extends AbstractBaseService {
    /**
     *
     * @author Nidhi SHukla
     */
    public static final String authPath = "/auth";
    public static final String createBookingPath = "/booking";
    public static final String deleteBookingPath = "/booking/";
    private final Reservations reservations;

    public ReservationService(Configuration configuration, Reservations reservations) {
        super(configuration.getProperties().getProperty("base.path"));
        this.reservations = reservations;
    }
//method executes the get authorization token request
    public void getAuthKey(AuthRequest auth) {
        var result = post(authPath, null, auth);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_OK);
        reservations.setAuthResponse(result.response().as(AuthResponse.class));
    }
// method executes the create booking request
    public void createBooking(CreateBookingRequest bookingRequest) {
        var result = post(createBookingPath, null, bookingRequest);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_OK);
        reservations.setCreateBookingResponse(result.response().as(CreateBookingResponse.class));
    }
    // method executes the update booking request
    public void updateBooking(CreateBookingRequest bookingRequest) {
        bookingRequest.setFirstname("updaterequest");
        String bookingId = reservations.getCreateBookingResponse().getBookingid();
        String token = reservations.getAuthResponse().getToken();
        var result = put(deleteBookingPath + bookingId, createHttpHeaders(token), bookingRequest);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    // method executes the cancel booking request
    public void cancelHotelReservation() {
        String bookingId = reservations.getCreateBookingResponse().getBookingid();
        String token = reservations.getAuthResponse().getToken();
        var result = delete(deleteBookingPath + bookingId, createHttpHeaders(token), null);
        assertThat(result.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
    }

    // This method asserts most part of the createBooking response
    public void assertBookingCreationwithtestdata(ArrayList response) {
        assertThat(reservations.getCreateBookingResponse().getBookingid()).isNotEmpty();
        assertThat(reservations.getCreateBookingResponse().getBooking().getFirstname()).isEqualTo(
                response.get(1).toString());
        assertThat(reservations.getCreateBookingResponse().getBooking().getLastname()).isEqualTo(
                response.get(2).toString());

        assertThat(reservations.getCreateBookingResponse().getBooking().getTotalprice()).isEqualTo(
               Integer.valueOf(response.get(3).toString()));
        assertThat(reservations.getCreateBookingResponse().getBooking().getDepositpaid()).isEqualTo(
                Boolean.valueOf(response.get(4).toString()));
        assertThat(reservations.getCreateBookingResponse().getBooking().getAdditionalneeds()).isEqualTo(
                response.get(5).toString());
    }

}
