package com.depalma.froneus.challenge.interfaces.controller;

import com.depalma.froneus.challenge.application.services.UserService;
import com.depalma.froneus.challenge.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Actualiza el estado de un usuario (activo/inactivo)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del usuario actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Petición incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del usuario a actualizar",
            required = true,
            content = @Content(schema = @Schema(implementation = User.class))
    )
    @PostMapping("/status")
    public ResponseEntity<Void> updateUserStatus(@RequestBody User user) {
        userService.updateUserStatus(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
