package ticketflow.ticket.service;

public interface EmailService {

    void sendBookingConfirmation(String toEmail, String customerName, String eventTitle, int ticketsCount);
}
