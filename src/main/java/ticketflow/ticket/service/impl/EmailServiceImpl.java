package ticketflow.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ticketflow.ticket.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendBookingConfirmation(String toEmail, String customerName,
                                        String eventTitle, int ticketsCount) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Booking Confirmation - " + eventTitle);
            message.setText(buildEmailBody(customerName, eventTitle, ticketsCount));

            mailSender.send(message);
            log.info("Booking confirmation email sent to: {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send booking confirmation email to: {}", toEmail, e);
        }
    }

    private String buildEmailBody(String customerName, String eventTitle, int ticketsCount) {
        return String.format("""
                Dear %s,

                Thank you for your booking! Here are your booking details:

                Event: %s
                Number of Tickets: %d

                We look forward to seeing you at the event!

                Best regards,
                TicketFlow Team
                """, customerName, eventTitle, ticketsCount);
    }
}

