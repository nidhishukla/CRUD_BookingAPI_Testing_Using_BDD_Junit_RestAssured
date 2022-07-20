@test
  Feature: Make a hotel reservation
    Scenario: As a user I can make a hotel reservation with a correct key and information
      When User receives authorisation key
      Then User can create a hotel reservation
      And Reservation created with the correct information
      And User updated the reservation details
      And User partially updated the reservation details
      And User can cancel the hotel reservation