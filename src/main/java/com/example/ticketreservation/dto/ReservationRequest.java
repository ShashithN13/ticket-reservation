package com.example.ticketreservation.dto;
import jakarta.validation.constraints.*;
public record ReservationRequest(@NotBlank String eventId, @Min(1) @Max(10) int seats) {}
