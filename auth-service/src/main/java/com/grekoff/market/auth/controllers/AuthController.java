package com.grekoff.market.auth.controllers;


import com.grekoff.market.auth.dto.JwtRequest;
import com.grekoff.market.auth.dto.JwtResponse;
import com.grekoff.market.auth.exceptions.AppError;
import com.grekoff.market.auth.services.UsersService;
import com.grekoff.market.auth.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authenticate")
//@CrossOrigin("*")
@Tag(name = "Авторизация", description = "Методы работы с токенами")
public class AuthController {

    private final UsersService usersService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    // http://localhost:8187/market-auth/api/v1

    @Operation(
            summary = "Запрос на авторизацию пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = JwtResponse.class))
                    ),
                    @ApiResponse(
                            description = "Имя пользователя или пароль не найдены", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь отсутствует в списке", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/token")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Имя пользователя или пароль не найдены"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = usersService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


}
