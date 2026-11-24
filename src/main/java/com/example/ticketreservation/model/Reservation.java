package com.example.ticketreservation.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
@Document(collection="reservations")
public class Reservation {
    @Id private String id;
    private String reservationCode;
    private String customer;
    private String eventId;
    private int seats;
    private BigDecimal amount;
    private ReservationStatus status;

    public Reservation() {}

    public Reservation(String id, String reservationCode, String customer, String eventId,
                       int seats, BigDecimal amount, ReservationStatus status) {
        this.id = id;
        this.reservationCode = reservationCode;
        this.customer = customer;
        this.eventId = eventId;
        this.seats = seats;
        this.amount = amount;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getReservationCode() { return reservationCode; }
    public void setReservationCode(String reservationCode) { this.reservationCode = reservationCode; }
    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}
