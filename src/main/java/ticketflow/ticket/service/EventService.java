package ticketflow.ticket.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ticketflow.ticket.dto.request.EventCreateRequestDTO;
import ticketflow.ticket.dto.response.EventResponseDTO;
import ticketflow.ticket.dto.response.OrganizerDashboardDTO;

import java.util.List;

public interface EventService {

    Page<EventResponseDTO> getAllEvents(Pageable pageable);

    EventResponseDTO getEventById(String id);

    EventResponseDTO createEvent(EventCreateRequestDTO request, String organizerId);

    List<OrganizerDashboardDTO> getOrganizerDashboard(String organizerId);
}

