package ticketflow.ticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerDashboardDTO {

    private String id;
    private String title;
    private String description;
    private String venue;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private Double ticketPrice;
    private String imageUrl;
    private LocalDateTime createdAt;
    private long totalBookings;
}
