package com.example.ticketreservation.repository;
import com.example.ticketreservation.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface ReservationRepository extends MongoRepository<Reservation,String> {
    List<Reservation> findByCustomer(String customer);
    List<Reservation> findByEventId(String eventId);
    boolean existsByEventId(String eventId);
    long countByStatus(String status);
    List<Reservation> findByStatus(com.example.ticketreservation.model.ReservationStatus status);
}
