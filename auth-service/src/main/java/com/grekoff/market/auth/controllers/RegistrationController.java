package com.grekoff.market.auth.controllers;

import com.grekoff.market.api.auth.UserRegistrationDto;
import com.grekoff.market.auth.entities.User;
import com.grekoff.market.auth.exceptions.AppError;
import com.grekoff.market.auth.exceptions.EmailExistsException;
import com.grekoff.market.auth.exceptions.UserAlreadyExistException;
import com.grekoff.market.auth.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
@Tag(name = "Регистрация", description = "Регистрация новых пользователей")
public class RegistrationController {
    private final UsersService usersService;

    @Operation(
            summary = "Запрос на регистрацию новго пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class))
                    ),
                    @ApiResponse(
                            description = "Пароли не совпадают", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь с таким именем уже существует", responseCode = "406",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )


            }
    )
    @PostMapping
    public ResponseEntity<?> registerUserAccount(@RequestBody @Parameter(description = "Данные формы регистрации пользователя", required = true) UserRegistrationDto userRegistrationDto) throws UserAlreadyExistException, EmailExistsException {
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }

        User registered = usersService.registerNewUserAccount(userRegistrationDto);
        if (registered==null){
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_ACCEPTABLE.value(), "Пользователь с таким именем уже существует"), HttpStatus.BAD_REQUEST);
        }

        userRegistrationDto.setUsername(null);
        userRegistrationDto.setEmail(null);
        userRegistrationDto.setFirstname(null);
        userRegistrationDto.setLastname(null);
        userRegistrationDto.setPassword(null);
        userRegistrationDto.setConfirmPassword(null);
        return  ResponseEntity.ok(userRegistrationDto);
    }
}
