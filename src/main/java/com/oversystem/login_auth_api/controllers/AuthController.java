package com.oversystem.login_auth_api.controllers;

import org.apache.el.stream.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oversystem.login_auth_api.domain.user.User;
import com.oversystem.login_auth_api.dto.LoginRequestDTO;
import com.oversystem.login_auth_api.dto.RegisterRequestDTO;
import com.oversystem.login_auth_api.dto.ResponseDTO;
import com.oversystem.login_auth_api.infra.security.TokenService;
import com.oversystem.login_auth_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
           User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
           if(passwordEncoder.matches(user.getPassword(), body.password())){
             String token = this.tokenService.generateToken(user);
             return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
           }

           return ResponseEntity.badRequest().build();
        }

        @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
          java.util.Optional <User> user = this.repository.findByEmail(body.email());

          if(user.isEmpty()){
           User newUser =  new User();
           newUser.setName(body.name());
           newUser.setEmail(body.email());
           newUser.setPassword(passwordEncoder.encode(body.password()));
           this.repository.save(newUser);

             String token = this.tokenService.generateToken(newUser);
             return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
          }

  
            

           return ResponseEntity.badRequest().build();
        }    

}
