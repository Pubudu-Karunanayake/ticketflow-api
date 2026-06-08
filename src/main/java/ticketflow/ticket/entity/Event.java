package ticketflow.ticket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "events")
public class Event {

    @Id
    private String id;

    private String title;

    private String description;

    private String venue;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private Double ticketPrice;

    private String imageUrl;

    private String organizerId;

    @CreatedDate
    private LocalDateTime createdAt;
}

