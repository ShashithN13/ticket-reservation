package com.example.ticketreservation.service;
import com.example.ticketreservation.dto.*;
import com.example.ticketreservation.exception.ApiException;
import com.example.ticketreservation.model.Event;
import com.example.ticketreservation.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EventService {
    private final EventRepository events; private final ReservationRepository reservations;
    public EventService(EventRepository events,ReservationRepository reservations) { this.events=events; this.reservations=reservations; }
    public EventResponse create(EventRequest r,String organizer) {
        Event e=new Event(null,r.name(),r.description(),r.venue(),r.eventDate(),r.totalSeats(),r.totalSeats(),r.ticketPrice(),organizer);
        return toResponse(events.save(e));
    }
    public List<EventResponse> all() { return events.findAll().stream().map(this::toResponse).toList(); }
    public EventResponse get(@NonNull String id) { return toResponse(find(id)); }
    public EventResponse update(@NonNull String id, EventRequest r, String username, boolean admin) {
        Event e = find(id);
        if (!admin && !e.getOrganizer().equals(username)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Not your event");
        }
        int bookedSeats = e.getTotalSeats() - e.getAvailableSeats();
        if (r.totalSeats() < bookedSeats) {
            throw new ApiException(HttpStatus.CONFLICT, "Total seats below already booked seats");
        }
        e.setName(r.name());
        e.setDescription(r.description());
        e.setVenue(r.venue());
        e.setEventDate(r.eventDate());
        e.setTotalSeats(r.totalSeats());
        e.setAvailableSeats(r.totalSeats() - bookedSeats);
        e.setTicketPrice(r.ticketPrice());
        events.save(e);
        return toResponse(e);
    }
    public void delete(@NonNull String id) {
        if (reservations.existsByEventId(id)) {
            throw new ApiException(HttpStatus.CONFLICT, "Event has reservations");
        }
        events.delete(find(id));
    }
    @NonNull public Event find(@NonNull String id) {
        Event event = events.findById(id).orElse(null);
        if (event == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Event not found");
        }
        return java.util.Objects.requireNonNull(event);
    }
    public EventResponse toResponse(Event e) { return new EventResponse(e.getId(),e.getName(),e.getDescription(),e.getVenue(),e.getEventDate(),e.getTotalSeats(),e.getAvailableSeats(),e.getTicketPrice(),e.getOrganizer()); }
}
