package ticketflow.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ticketflow.ticket.dto.request.LoginRequestDTO;
import ticketflow.ticket.dto.request.SignupRequestDTO;
import ticketflow.ticket.dto.response.AuthResponseDTO;
import ticketflow.ticket.entity.User;
import ticketflow.ticket.enums.Role;
import ticketflow.ticket.exception.DuplicateResourceException;
import ticketflow.ticket.repository.UserRepository;
import ticketflow.ticket.security.JwtUtil;
import ticketflow.ticket.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO signup(SignupRequestDTO request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("An account with this email already exists");
        }

        // Build and save user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ORGANIZER)
                .build();

        User savedUser = userRepository.save(user);

        // Generate JWT token with role embedded
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().name());

        return AuthResponseDTO.builder()
                .token(token)
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // Generate JWT token with role embedded
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponseDTO.builder()
                .token(token)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}

