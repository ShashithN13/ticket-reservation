package com.example.ticketreservation.service;
import com.example.ticketreservation.dto.StatsResponse;
import com.example.ticketreservation.model.ReservationStatus;
import com.example.ticketreservation.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Objects;
@Service
public class AdminService {
    private final UserRepository users; private final EventRepository events; private final ReservationRepository reservations;
    public AdminService(UserRepository users,EventRepository events,ReservationRepository reservations) { this.users=users; this.events=events; this.reservations=reservations; }
    public StatsResponse stats() {
        var confirmed=reservations.findByStatus(ReservationStatus.CONFIRMED);
        long sold = 0;
        BigDecimal revenue = BigDecimal.ZERO;
        for (var reservation : confirmed) {
            sold += reservation.getSeats();
            revenue = revenue.add(Objects.requireNonNull(reservation.getAmount()));
        }
        return new StatsResponse(users.count(),events.count(),sold,revenue);
    }
}
