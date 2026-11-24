package com.example.ticketreservation.controller;
import com.example.ticketreservation.dto.*;
import com.example.ticketreservation.service.EventService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/events")
public class EventController {
    private final EventService service;
    public EventController(EventService service) { this.service=service; }
    @GetMapping public List<EventResponse> all() { return service.all(); }
    @GetMapping("/{id}") public EventResponse get(@PathVariable @NonNull String id) { return service.get(id); }
    @PostMapping @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public EventResponse create(@Valid @RequestBody EventRequest r,Authentication a) { return service.create(r,a.getName()); }
    @PutMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public EventResponse update(@PathVariable @NonNull String id,@Valid @RequestBody EventRequest r,Authentication a) {
        boolean admin=a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN"));
        return service.update(id,r,a.getName(),admin);
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable @NonNull String id) { service.delete(id); }
}
