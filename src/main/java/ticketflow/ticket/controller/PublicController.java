package ticketflow.ticket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ticketflow.ticket.dto.request.BookingRequestDTO;
import ticketflow.ticket.dto.response.ApiResponseDTO;
import ticketflow.ticket.dto.response.EventResponseDTO;
import ticketflow.ticket.service.BookingService;
import ticketflow.ticket.service.EventService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicController {

    private final EventService eventService;
    private final BookingService bookingService;

    @GetMapping("/events")
    public ResponseEntity<ApiResponseDTO<Page<EventResponseDTO>>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<EventResponseDTO> events = eventService.getAllEvents(pageable);

        return ResponseEntity.ok(ApiResponseDTO.success("Events fetched successfully", events));
    }
    

    @GetMapping("/events/{id}")
    public ResponseEntity<ApiResponseDTO<EventResponseDTO>> getEventById(@PathVariable String id) {
        EventResponseDTO event = eventService.getEventById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Event fetched successfully", event));
    }


    @PostMapping("/bookings")
    public ResponseEntity<ApiResponseDTO<String>> createBooking(
            @Valid @RequestBody BookingRequestDTO request) {
        String message = bookingService.createBooking(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(message));
    }
}
