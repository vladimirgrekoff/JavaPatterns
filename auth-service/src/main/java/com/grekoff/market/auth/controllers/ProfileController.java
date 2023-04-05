package com.grekoff.market.auth.controllers;

import com.grekoff.market.api.auth.RoleDto;
import com.grekoff.market.api.auth.UserDto;
import com.grekoff.market.auth.converters.RoleConverter;
import com.grekoff.market.auth.converters.UserConverter;
import com.grekoff.market.auth.entities.User;
import com.grekoff.market.auth.exceptions.AppError;
import com.grekoff.market.auth.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
//@CrossOrigin("*")
@Tag(name = "Профили пользователей", description = "Методы работы с профилями пользователей")
public class ProfileController {
    private final UsersService usersService;
    private final RoleConverter roleConverter;
    private final UserConverter userConverter;

    // http://localhost:8187/market-auth/api/v1/profile

    @Operation(
            summary = "Запрос на получение пользователя по username",
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
    @GetMapping("/get")
    public UserDto getCurrentUserInfo(@RequestHeader @Parameter(description = "username", required = true) String username) {
         User user = usersService.findByUsername(username).get();
         return userConverter.entityToDto(user);
    }

    @Operation(
            summary = "Запрос на получение списка ролей пользователя по username",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = RoleDto.class)))}
                    ),
                    @ApiResponse(
                            description = "Пользователь отсутствует в списке", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/roles")
    public ResponseEntity<?> getCurrentRoles(@RequestHeader @Parameter(description = "username", required = true) String username) {
        User user = usersService.findByUsername(username).get();
//        return ResponseEntity.ok(new RoleDto("", user.getUsername(), "", "", "", user.getRoles().stream().map(roleConverter::entityToDto).collect(Collectors.toList())));
        List<RoleDto> roles = user.getRoles().stream().map(roleConverter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }
}
