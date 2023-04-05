package com.grekoff.market.auth.controllers;

import com.grekoff.market.auth.converters.UserConverter;
import com.grekoff.market.api.auth.UserDto;
import com.grekoff.market.auth.entities.User;
import com.grekoff.market.auth.exceptions.AppError;
import com.grekoff.market.auth.exceptions.EmailExistsException;
import com.grekoff.market.auth.exceptions.ResourceNotFoundException;
import com.grekoff.market.auth.exceptions.UserAlreadyExistException;
import com.grekoff.market.auth.services.UsersService;
import com.grekoff.market.auth.validators.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Администрирование", description = "Методы работы с данными пользователей")
public class UsersController {

    private final UsersService usersService;
    private final UserConverter userConverter;
    private final UserValidator userValidator;


    // GET http://localhost:8189/market-auth/api/v1/users

    @Operation(
            summary = "Запрос на получение полного списка пользователей",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))}
                    )
            }
    )
    @GetMapping
    public List<UserDto> getAllUsers() {
        return usersService.findAll();
    }


    @Operation(
            summary = "Запрос на получение пользователя по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь отсутствует в списке", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable @Parameter(description = "Идентификатор пользователя", required = true)  Long id) {
        User user = usersService.findById(id).orElseThrow(()-> new ResourceNotFoundException("Пользователь отсутствует в списке, id: " + id));
        return userConverter.entityToDto(user);
    }


    @Operation(
            summary = "Запрос на создание нового пользователя",
            responses = {
                    @ApiResponse(
                            description = "Пользователь успешно создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь с таким именем уже существует", responseCode = "406",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )

            }
    )
    @PostMapping
    public UserDto addNewUser(@RequestBody @Parameter(description = "Новые данные пользователя", required = true) UserDto userDto) throws UserAlreadyExistException, EmailExistsException {
        userValidator.validate(userDto);
        User user = userConverter.dtoToEntity(userDto);
        user = usersService.save(user);
        return userConverter.entityToDto(user);
    }


    @Operation(
            summary = "Запрос на изменение данных пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь отсутствует в списке", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PutMapping
    public UserDto updateUser(@RequestBody @Parameter(description = "Новые данные пользователя", required = true) UserDto userDto) {
        userValidator.validate(userDto);
        User user = usersService.update(userDto);
        return userConverter.entityToDto(user);
    }


    @Operation(
            summary = "Запрос на удаление пользователя по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @DeleteMapping ("/{id}")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор пользователя", required = true) Long id){
        usersService.deleteById(id);
    }

}
