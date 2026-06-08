package ticketflow.ticket.service;

import ticketflow.ticket.dto.request.BookingRequestDTO;

public interface BookingService {

    String createBooking(BookingRequestDTO request);
}
