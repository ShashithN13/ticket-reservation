# Ticket Reservation Lab 08

## Requirements
- Java 17
- Maven
- MongoDB running at localhost:27017
- IntelliJ IDEA or VS Code

## Run
1. Open this folder as a Maven project.
2. Ensure MongoDB is running.
3. Run `mvn spring-boot:run`.
4. API base URL: http://localhost:8080

## Seed accounts
- admin / admin123
- organizer / organizer123
- customer / customer123

## Postman flow
1. POST /api/auth/login with username/password to receive JWT.
2. For protected requests set Authorization > Bearer Token.
3. POST /api/events as organizer:
   {"name":"My Concert","description":"Lab event","venue":"Colombo","eventDate":"2030-12-31T18:00:00","totalSeats":20,"ticketPrice":1000}
4. POST /api/reservations as customer:
   {"eventId":"<event id>","seats":2}
5. GET /api/reservations/my as customer.
6. DELETE /api/reservations/<reservation id> as customer.

## Important notes
- Public registration intentionally creates CUSTOMER accounts only, as specified in the lab scenario.
- For production-grade concurrent booking, replace the read-modify-save seat update in ReservationService with an atomic MongoDB find-and-modify update. The lab learning outcome explicitly calls for atomic seat handling; this starter implementation is simplified and should be upgraded before claiming concurrency safety.
- The project includes all class names listed in Lab Sheet 08. Review the lab sheet and test each endpoint in Postman before submission.
