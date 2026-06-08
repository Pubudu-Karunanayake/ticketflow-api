package ticketflow.ticket.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ticketflow.ticket.entity.Event;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByOrganizerId(String organizerId);
}
