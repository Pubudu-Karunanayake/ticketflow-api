package ticketflow.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ticketflow.ticket.dto.request.EventCreateRequestDTO;
import ticketflow.ticket.dto.response.EventResponseDTO;
import ticketflow.ticket.dto.response.OrganizerDashboardDTO;
import ticketflow.ticket.entity.Event;
import ticketflow.ticket.exception.ResourceNotFoundException;
import ticketflow.ticket.repository.BookingRepository;
import ticketflow.ticket.repository.EventRepository;
import ticketflow.ticket.service.CloudinaryService;
import ticketflow.ticket.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public Page<EventResponseDTO> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::mapToEventResponseDTO);
    }

    @Override
    public EventResponseDTO getEventById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        return mapToEventResponseDTO(event);
    }

    @Override
    public EventResponseDTO createEvent(EventCreateRequestDTO request, String organizerId) {
        // Upload image to Cloudinary
        String imageUrl = cloudinaryService.uploadImage(request.getImage());

        // Build and save event
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .venue(request.getVenue())
                .eventDate(request.getEventDate())
                .eventTime(request.getEventTime())
                .ticketPrice(request.getTicketPrice())
                .imageUrl(imageUrl)
                .organizerId(organizerId)
                .build();

        Event savedEvent = eventRepository.save(event);
        log.info("Event created: {} by organizer: {}", savedEvent.getId(), organizerId);

        return mapToEventResponseDTO(savedEvent);
    }

    @Override
    public List<OrganizerDashboardDTO> getOrganizerDashboard(String organizerId) {
        List<Event> events = eventRepository.findByOrganizerId(organizerId);

        return events.stream()
                .map(event -> {
                    long totalBookings = bookingRepository.countByEventId(event.getId());
                    return OrganizerDashboardDTO.builder()
                            .id(event.getId())
                            .title(event.getTitle())
                            .description(event.getDescription())
                            .venue(event.getVenue())
                            .eventDate(event.getEventDate())
                            .eventTime(event.getEventTime())
                            .ticketPrice(event.getTicketPrice())
                            .imageUrl(event.getImageUrl())
                            .createdAt(event.getCreatedAt())
                            .totalBookings(totalBookings)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private EventResponseDTO mapToEventResponseDTO(Event event) {
        return EventResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .venue(event.getVenue())
                .eventDate(event.getEventDate())
                .eventTime(event.getEventTime())
                .ticketPrice(event.getTicketPrice())
                .imageUrl(event.getImageUrl())
                .organizerId(event.getOrganizerId())
                .createdAt(event.getCreatedAt())
                .build();
    }
}

