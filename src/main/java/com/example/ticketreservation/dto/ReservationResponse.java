package com.example.ticketreservation.dto;
import java.math.BigDecimal;
public record ReservationResponse(String id, String reservationCode, String customer,
 String eventId, int seats, BigDecimal amount, String status) {}
