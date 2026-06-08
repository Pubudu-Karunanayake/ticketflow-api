package ticketflow.ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketflow.ticket.dto.request.EventCreateRequestDTO;
import ticketflow.ticket.dto.response.ApiResponseDTO;
import ticketflow.ticket.dto.response.EventResponseDTO;
import ticketflow.ticket.dto.response.OrganizerDashboardDTO;
import ticketflow.ticket.entity.User;
import ticketflow.ticket.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/organizer")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ORGANIZER')")
public class OrganizerController {

    private final EventService eventService;

    @PostMapping(value = "/events", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO<EventResponseDTO>> createEvent(
            @Valid @ModelAttribute EventCreateRequestDTO request,
            @AuthenticationPrincipal User user) {

        EventResponseDTO event = eventService.createEvent(request, user.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Event created successfully", event));
    }

    @GetMapping("/events")
    public ResponseEntity<ApiResponseDTO<List<OrganizerDashboardDTO>>> getOrganizerDashboard(
            @AuthenticationPrincipal User user) {

        List<OrganizerDashboardDTO> dashboard = eventService.getOrganizerDashboard(user.getId());

        return ResponseEntity.ok(ApiResponseDTO.success("Dashboard fetched successfully", dashboard));
    }
}

