package ticketflow.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ticketflow.ticket.dto.request.BookingRequestDTO;
import ticketflow.ticket.entity.Booking;
import ticketflow.ticket.entity.Event;
import ticketflow.ticket.exception.ResourceNotFoundException;
import ticketflow.ticket.repository.BookingRepository;
import ticketflow.ticket.repository.EventRepository;
import ticketflow.ticket.service.BookingService;
import ticketflow.ticket.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final EmailService emailService;

    @Override
    public String createBooking(BookingRequestDTO request) {
        // Verify the event exists
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", request.getEventId()));

        // Build and save the booking
        Booking booking = Booking.builder()
                .eventId(request.getEventId())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .ticketsCount(request.getTicketsCount())
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created: {} for event: {}", savedBooking.getId(), event.getTitle());

        // Trigger confirmation email (async — won't block the response)
        emailService.sendBookingConfirmation(
                request.getCustomerEmail(),
                request.getCustomerName(),
                event.getTitle(),
                request.getTicketsCount()
        );

        return "Booking confirmed successfully! A confirmation email has been sent to " + request.getCustomerEmail();
    }
}
