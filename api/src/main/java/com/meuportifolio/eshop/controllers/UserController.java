package com.meuportifolio.eshop.controllers;

import com.meuportifolio.eshop.dto.UserDto;
import com.meuportifolio.eshop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "User", description = "Endpoints for managing users.")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Should return the list of users found")
    @ApiResponse(responseCode = "200", description = "Should return the list of users found if present or else empty list.")
    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Should return only one user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "204", description = "Should return user not found exception."),
            @ApiResponse(responseCode = "400", description = "Bad Request.")
    })
    @GetMapping(value = "/{id}")
    public UserDto findById(@PathVariable @Positive Long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Should insert a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created with success."),
            @ApiResponse(responseCode = "400", description = "Should return bad request.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto insert(@RequestBody @Valid UserDto obj) {
        return userService.insert(obj);
    }

    @Operation(summary = "Should update an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated with success."),
            @ApiResponse(responseCode = "204", description = "Should return user not found exception."),
            @ApiResponse(responseCode = "400", description = "Bad Request.")
    })
    @PutMapping(value = "/{id}")
    public UserDto update(@PathVariable @Positive Long id, @RequestBody @Valid UserDto obj) {
        return userService.update(id, obj);
    }
}
