package com.example.ticketreservation.controller;
import com.example.ticketreservation.dto.*;
import com.example.ticketreservation.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/admin") @PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AuthService auth; private final AdminService admin;
    public AdminController(AuthService auth,AdminService admin) { this.auth=auth; this.admin=admin; }
    @GetMapping("/users") public List<UserResponse> users() { return auth.listUsers(); }
    @GetMapping("/stats") public StatsResponse stats() { return admin.stats(); }
}
