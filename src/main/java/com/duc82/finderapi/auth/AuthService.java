package com.duc82.finderapi.auth;

import com.duc82.finderapi.auth.dtos.*;
import com.duc82.finderapi.configs.JwtUtil;
import com.duc82.finderapi.exceptions.BadRequestException;
import com.duc82.finderapi.users.UserRepository;
import com.duc82.finderapi.users.UserService;
import com.duc82.finderapi.users.entities.Profile;
import com.duc82.finderapi.users.entities.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${finder.oauth2.google.client-id}")
    private String clientId;


    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new BadRequestException("Invalid email or password"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword()
        ));
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToke(user);

        return SignInResponse.builder().accessToken(accessToken).refreshToken(refreshToken).user(user).message("Sign in successfully").build();
    }

    public SignInResponse signInGoogle(String token) throws GeneralSecurityException, IOException {
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId)).build();
        GoogleIdToken idToken = verifier.verify(token);

        if (idToken == null) {
            throw new BadRequestException("Invalid token");
        }

        Payload payload = idToken.getPayload();

        // Print user identifier
        String userId = payload.getSubject();

        // Get profile information from payload
        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        // Use or store profile information
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            Profile profile = new Profile();
            profile.setAvatar(pictureUrl);
            newUser.setEmail(email);
            newUser.setFullName(name);
            newUser.setVerifiedAt(emailVerified ? newUser.getCreatedAt() : null);
            newUser.setProfile(profile);
            return userRepository.save(newUser);
        });

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToke(user);

        return SignInResponse.builder().accessToken(accessToken).refreshToken(refreshToken).user(user).message("Sign in successfully").build();
    }



    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Boolean exists = userService.existsByEmail(signUpRequest.getEmail());
        if (exists) {
            throw new BadRequestException("Email already exists");
        }
        userService.save(signUpRequest);
        return new SignUpResponse("Sign up successfully");
    }

    public RefreshTokenResponse refreshToken(String refreshToken) {
        String email = jwtUtil.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        String accessToken = jwtUtil.generateToken(user);

        return new RefreshTokenResponse(accessToken, "Refresh token is refreshed");
    }

}
