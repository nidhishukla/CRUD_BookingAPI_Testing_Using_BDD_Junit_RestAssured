package com.foundations.reservation.steps;

import com.foundations.common.config.Configuration;
import com.foundations.common.config.TestConstants;
import com.foundations.common.model.AuthRequest;
import com.foundations.common.model.BookingDates;
import com.foundations.common.model.CreateBookingRequest;
import com.foundations.dataDriven;
import com.foundations.reservation.service.ReservationService;
import io.cucumber.java8.En;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReservationSteps implements En {
    /**
     *
     * @author Nidhi SHukla
     */
    private final ReservationService reservationService;
    private final Configuration configuration;
    HashMap<String, Object>  map = new HashMap<>();
    ArrayList data ;

    public ReservationSteps(ReservationService reservationService,
            Configuration configuration) throws IOException {
        setExcelData();
        this.reservationService = reservationService;
        this.configuration = configuration;
        When("^User receives authorisation key$", this::getAuthorisation);
        Then("^User can create a hotel reservation$", this::createHotelReservation);
        Then("^Reservation created with the correct information$", this::assertCreateReservation);
        Then("^User can cancel the hotel reservation$", this::cancelHotelReservation);
        Then("User updated the reservation details", this::updateHotelReservation);

    }

    private void getAuthorisation() {
        reservationService.getAuthKey(auth());
    }
    private void createHotelReservation() {
        reservationService.createBooking(createBookingRequest());
    }
    private void updateHotelReservation() {
        reservationService.updateBooking(updateBookingRequest());
    }
    private void cancelHotelReservation() {
        reservationService.cancelHotelReservation();
    }
    private void assertCreateReservation() {
        reservationService.assertBookingCreationwithtestdata(data);
    }

    private AuthRequest auth() {
        AuthRequest auth = new AuthRequest();
        auth.setUsername(configuration.getProperties().getProperty("username"));
        auth.setPassword(configuration.getProperties().getProperty("password"));
        return auth;
    }
    private CreateBookingRequest createBookingRequest() {
        CreateBookingRequest createBookingRequest = new CreateBookingRequest();
        createBookingRequest.setFirstname(data.get(1).toString());
        createBookingRequest.setLastname(data.get(2).toString());
        createBookingRequest.setTotalprice(Integer.valueOf(data.get(3).toString()));
        createBookingRequest.setDepositpaid(Boolean.valueOf(data.get(4).toString()));
        createBookingRequest.setBookingdates(bookingDates());
        createBookingRequest.setAdditionalneeds(data.get(5).toString());
        return createBookingRequest;
    }

    private CreateBookingRequest updateBookingRequest() {
        CreateBookingRequest createBookingRequest = new CreateBookingRequest();
        createBookingRequest.setFirstname(data.get(6).toString());
        createBookingRequest.setLastname(data.get(2).toString());
        createBookingRequest.setTotalprice(Integer.valueOf(data.get(3).toString()));
        createBookingRequest.setDepositpaid(Boolean.valueOf(data.get(4).toString()));
        createBookingRequest.setBookingdates(bookingDates());
        createBookingRequest.setAdditionalneeds(data.get(5).toString());
        return createBookingRequest;
    }
    private BookingDates bookingDates() {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2021-01-01");
        bookingDates.setCheckout("2022-01-01");
        return bookingDates;
    }
   void setExcelData() throws IOException {
     dataDriven d=new dataDriven();
      data=d.getData("bookingPositiveScenario","Booking");
 }
}
