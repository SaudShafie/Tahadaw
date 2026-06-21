package org.example.tahadaw.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiResponse;
import org.example.tahadaw.DTO.IN.UserDTOIn;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTOIn userDTOIn) {
        userService.addUser(userDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("User registered successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user, @Valid @RequestBody UserDTOIn userDTOIn) {
        userService.updateUser(user.getId(), userDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }


}
