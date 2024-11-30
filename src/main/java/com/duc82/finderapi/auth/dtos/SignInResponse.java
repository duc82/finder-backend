package com.duc82.finderapi.auth.dtos;

import com.duc82.finderapi.users.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private User user;
    private String message;
    private String accessToken;
    private String refreshToken;
}
