# Parking App

## Downtown parking is ~~hard~~ easy

Have you ever been stuck searching for parking spots at a crowded mall?
This application easily lets you search available parking spots and book them!

This application is made for the people of vancouver who wants to speed up parking time,
and for building managers that want to decrease traffic inside basements and parking lots.\
Parking could be troublesome for people as parking spots are limited while hangout spots continuously increase.
Use this app to speed up parking so that you don't waste time on finding an empty parking space.

Focus more on what matters rather than parking.


**Simplify *your* life by:**
- Reserve empty parking spots near your destination
- Identify popular parking spots 
- Lookup parking prices, maximum hours, and accessibility

## User Stories
- As a user, I want to be able to add My reservation to the Parking Spots.
- As a user, I want to be able to search places for available Parking Spots.
- As a user, I want to be able to list prices, maximum hours, and accessibility of parking spots.
- As a user, I want to be able to cancel my reservation to the Parking Spots.
- As a user, when I quit from the program, it saves my account.
- As a user, when I type in my name and email, I want to continue from where I left off.

##How to use
- Login to the app or create a new account
- To deposit some money, press the deposit button, then slide to the appropriate value, then press click
- To view your reservation, press the view button. Your reservations are displayed in a table.
- To cancel any reservation, click the reservation at the table, and then press cancel.
- To reserve, press the reservation button. Then, choose the destination by pressing the list, then press the press
the button. Then, pick which parking spot to reserve, the time, and the duration for the reservation.
Notice: there's an indication on when the parking spot is available. [0 - 23] means its available all day.
[0 - 5], [8 - 23] means that its available from 00:00 - 5:59 and 8:00 - 23:59.
After picking the time, confirm the reservation and press yes.

##Phase 4: Task 2
I have implemented a robust persistence class called JsonReader and the tested it in JsonReaderTest.
The constructor on JsonReader is robust, and the method findall() is also robust.

##Phase 4: task 3
There is low cohesion in the Account class, where there are fields describing the account and a reservation field 
describing all of the reservation of a single account.
I made changes to this by creating a new class called AccountReservationManger for the Account class to use.
This class provides services on adding reservation and removing reservation and making sure that removing a reservation
also cancels the reservation on the parkingspot.