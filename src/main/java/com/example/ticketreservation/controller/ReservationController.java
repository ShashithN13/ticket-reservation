package com.example.ticketreservation.controller;
import com.example.ticketreservation.dto.*;
import com.example.ticketreservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;
@RestController @RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService service;
    public ReservationController(ReservationService service) { this.service=service; }
    @PostMapping @PreAuthorize("hasRole('CUSTOMER')")
    public ReservationResponse book(@Valid @RequestBody ReservationRequest r,Authentication a) { return service.book(r,Objects.requireNonNull(a.getName())); }
    @GetMapping("/my") @PreAuthorize("hasRole('CUSTOMER')")
    public List<ReservationResponse> mine(Authentication a) { return service.mine(a.getName()); }
    @GetMapping @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public List<ReservationResponse> all(Authentication a) {
        boolean admin=a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN"));
        return admin?service.all():service.forOrganizer(a.getName());
    }
    @DeleteMapping("/{id}") @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public void cancel(@PathVariable String id,Authentication a) {
        boolean admin=a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN"));
        service.cancel(Objects.requireNonNull(id),Objects.requireNonNull(a.getName()),admin);
    }
}
