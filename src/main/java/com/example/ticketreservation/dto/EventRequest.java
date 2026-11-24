package com.example.ticketreservation.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record EventRequest(@NotBlank String name, String description, @NotBlank String venue,
 @Future LocalDateTime eventDate, @Min(1) int totalSeats, @DecimalMin("0.0") BigDecimal ticketPrice) {}
