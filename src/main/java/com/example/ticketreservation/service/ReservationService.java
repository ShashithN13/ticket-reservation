package com.example.ticketreservation.service;
import com.example.ticketreservation.dto.*;
import com.example.ticketreservation.exception.ApiException;
import com.example.ticketreservation.model.*;
import com.example.ticketreservation.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ReservationService {
    private final ReservationRepository reservations; private final EventRepository events;
    public ReservationService(ReservationRepository reservations,EventRepository events) { this.reservations=reservations; this.events=events; }
    public ReservationResponse book(ReservationRequest r, @NonNull String customer) {
        Event e = events.findById(Objects.requireNonNull(r.eventId()))
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Event not found"));
        if(e.getAvailableSeats()<r.seats()) throw new ApiException(HttpStatus.CONFLICT,"Not enough seats");
        e.setAvailableSeats(e.getAvailableSeats()-r.seats()); events.save(e);
        Reservation b=new Reservation(null,"TKT-"+UUID.randomUUID().toString().substring(0,8).toUpperCase(),customer,e.getId(),r.seats(),
          e.getTicketPrice().multiply(java.math.BigDecimal.valueOf(r.seats())),ReservationStatus.CONFIRMED);
        return toResponse(reservations.save(b));
    }
    public List<ReservationResponse> mine(String username) { return reservations.findByCustomer(username).stream().map(this::toResponse).toList(); }
    public List<ReservationResponse> all() { return reservations.findAll().stream().map(this::toResponse).toList(); }
    public List<ReservationResponse> forOrganizer(String username) {
        Set<String> ids=new HashSet<>(); events.findByOrganizer(username).forEach(e->ids.add(e.getId()));
        return reservations.findAll().stream().filter(r->ids.contains(r.getEventId())).map(this::toResponse).toList();
    }
    public void cancel(@NonNull String id, @NonNull String username, boolean admin) {
        Reservation r=reservations.findById(id).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"Reservation not found"));
        if(!admin && !r.getCustomer().equals(username)) throw new ApiException(HttpStatus.FORBIDDEN,"Not your reservation");
        if(r.getStatus()==ReservationStatus.CANCELLED) return;
        Event e = events.findById(Objects.requireNonNull(r.getEventId()))
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Event not found"));
        e.setAvailableSeats(e.getAvailableSeats()+r.getSeats()); events.save(e);
        r.setStatus(ReservationStatus.CANCELLED); reservations.save(r);
    }
    private ReservationResponse toResponse(Reservation r) { return new ReservationResponse(r.getId(),r.getReservationCode(),r.getCustomer(),r.getEventId(),r.getSeats(),r.getAmount(),r.getStatus().name()); }
}
