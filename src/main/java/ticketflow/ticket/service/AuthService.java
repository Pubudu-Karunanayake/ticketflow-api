package ticketflow.ticket.service;

import ticketflow.ticket.dto.request.LoginRequestDTO;
import ticketflow.ticket.dto.request.SignupRequestDTO;
import ticketflow.ticket.dto.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO signup(SignupRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}
