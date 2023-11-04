package com.jwt.demo.controller;

import com.jwt.demo.dto.JwtAuthRequest;
import com.jwt.demo.dto.JwtAuthResponse;
import com.jwt.demo.exception.JwtException;
import com.jwt.demo.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
        this.authenticate(jwtAuthRequest.getUserName(), jwtAuthRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUserName());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse jwtResponse = JwtAuthResponse.builder().token(token).userName(jwtAuthRequest.getUserName()).build();

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void authenticate(String userName, String password) throws Exception {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        try{
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("invalid credentials....");
            throw new JwtException("invalid credentials....");
        }
    }

}
