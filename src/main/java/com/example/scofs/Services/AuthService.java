package com.example.scofs.Services;

import com.example.loginsystem.Models.JwtResponse;
import com.example.loginsystem.Models.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private RestTemplate restTemplate;


    public String authenticate(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        ResponseEntity<JwtResponse> response = restTemplate.postForEntity(
                "http://localhost:8081/auth/login", loginRequest, JwtResponse.class);

        return response.getBody().getToken(); // Return the token to be used in SCOFS
    }
}
