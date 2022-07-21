@test
  Feature: Make a hotel booking and update the details and cancelling the booking
#POST Auth - CreateToken --Creates a new auth token to use for access to the PUT and DELETE /booking
    Scenario: As a user I can get the Auth token
      When User receives authorisation key

#Get Booking - GetBookingIds --Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids.
    Scenario: As a user I can get list of all bookings details
      When User receives authorisation key
      Then User received all booking details

#Get Booking - GetBooking -- Returns a specific booking based upon the booking id provided
    Scenario: As a user I can get bookings details
      When User receives authorisation key
      Then User can create a hotel reservation
      And User received booking details

#POST - Booking - CreateBooking--   Creates a new booking in the API
    Scenario: As a user I can make a hotel reservation with a correct key and information
      When User receives authorisation key
      Then User can create a hotel reservation
      And Reservation created with the correct information
      And User received booking details
      And User can cancel the hotel reservation

#PUT  Booking - UpdateBooking --  Updates a current booking
    Scenario: As a user I can make a update hotel booking with complete details
      When User receives authorisation key
      Then User can create a hotel reservation
      And Reservation created with the correct information
      And User updated the reservation details
      And User can cancel the hotel reservation


#PATCH  Booking - UpdateBooking --Updates a current booking
    Scenario: As a user I can make a partial update to hotel booking
      When User receives authorisation key
      Then User can create a hotel reservation
      And Reservation created with the correct information
      And User partially updated the reservation details
      And User can cancel the hotel reservation

#DELETE  Booking - DeleteBooking --Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids.
    Scenario: As a user I can cancel hotel booking
      When User receives authorisation key
      Then User can create a hotel reservation
      And Reservation created with the correct information
      And User can cancel the hotel reservation
