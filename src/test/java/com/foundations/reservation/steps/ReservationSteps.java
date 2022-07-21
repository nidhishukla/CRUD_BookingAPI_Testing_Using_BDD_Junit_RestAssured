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
        When("^User receives authorisation key$", this::getAuthorisation); //Auth - CreateToken --Creates a new auth token to use for access to the PUT and DELETE /booking

        Then("User received booking details",this::getBookingDetails); //Booking - GetBooking -- Returns a specific booking based upon the booking id provided

        Then("User received all booking details",this::getAllBookings); //Booking - GetBookingIds --Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids.

        Then("^User can create a hotel reservation$", this::createHotelBooking); //Booking - CreateBooking--   Creates a new booking in the API

        Then("^Reservation created with the correct information$", this::assertCreateReservation); //Assertions

        Then("User updated the reservation details", this::updateHotelBooking);//Booking - UpdateBooking --  Updates a current booking

        Then("User partially updated the reservation details",this::partiallyUpdateHotelBooking);// Booking - UpdateBooking --Updates a current booking

        Then("^User can cancel the hotel reservation$", this::cancelHotelReservation);//Booking - DeleteBooking --Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids.

    }

    private void getAuthorisation() {
        reservationService.getAuthKey(auth());
    }
    private void createHotelBooking() {
        reservationService.createBooking(createBookingRequest());
    }
    private void updateHotelBooking() {reservationService.updateBooking(updateBookingRequest());  }
    private void partiallyUpdateHotelBooking() {reservationService.partiallyUpdateBooking(partiallyUpdateHotelReservation());  }
    private void getAllBookings() {reservationService.getAllBookingList();  }

    private void getBookingDetails() {reservationService.getBookingdetails();  }

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
        createBookingRequest.setFirstname(data.get(8).toString());
        createBookingRequest.setLastname(data.get(9).toString());
        createBookingRequest.setTotalprice(Integer.valueOf(data.get(10).toString()));
        createBookingRequest.setDepositpaid(Boolean.valueOf(data.get(11).toString()));
        createBookingRequest.setBookingdates(bookingDates());
        createBookingRequest.setAdditionalneeds(data.get(12).toString());
        return createBookingRequest;
    }
    private CreateBookingRequest partiallyUpdateHotelReservation() {
        CreateBookingRequest createBookingRequest = new CreateBookingRequest();
        createBookingRequest.setFirstname(data.get(15).toString());
        createBookingRequest.setLastname(data.get(16).toString());
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
