package ticketflow.ticket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketflow.ticket.dto.request.LoginRequestDTO;
import ticketflow.ticket.dto.request.SignupRequestDTO;
import ticketflow.ticket.dto.response.ApiResponseDTO;
import ticketflow.ticket.dto.response.AuthResponseDTO;
import ticketflow.ticket.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> signup(
            @Valid @RequestBody SignupRequestDTO request) {
        AuthResponseDTO response = authService.signup(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Signup successful", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity
                .ok(ApiResponseDTO.success("Login successful", response));
    }
}
