package ticketflow.ticket.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ticketflow.ticket.entity.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByEventId(String eventId);

    long countByEventId(String eventId);
}
