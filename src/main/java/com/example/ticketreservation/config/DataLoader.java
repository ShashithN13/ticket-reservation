package com.example.ticketreservation.config;

import com.example.ticketreservation.model.AppUser;
import com.example.ticketreservation.model.Event;
import com.example.ticketreservation.model.Role;
import com.example.ticketreservation.repository.EventRepository;
import com.example.ticketreservation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(UserRepository users, EventRepository events, PasswordEncoder encoder) {
        return args -> {
            if (users.count() == 0) {
                users.save(new AppUser(null, "admin", encoder.encode("admin123"), "System Admin", Role.ADMIN));
                users.save(new AppUser(null, "organizer", encoder.encode("organizer123"), "Event Organizer", Role.ORGANIZER));
                users.save(new AppUser(null, "customer", encoder.encode("customer123"), "Test Customer", Role.CUSTOMER));
            }
            if (events.count() == 0) {
                events.save(new Event(null, "Sample Concert", "Lab 08 sample event", "Colombo",
                    LocalDateTime.of(2030, 12, 31, 18, 0), 20, 20, new BigDecimal("1000.00"), "organizer"));
            }
        };
    }
}
