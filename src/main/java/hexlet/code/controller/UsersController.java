package hexlet.code.controller;

import hexlet.code.dto.AuthRequest;
import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.JWTUtils;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    public UserRepository repository;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("/temp2")
    public String index2() {
        return "labuda2";
    }

    @GetMapping("/users")
    List<UserDTO> index() {
        var users = repository.findAll();
        return users.stream()
                .map(this::toDTO)
                .toList();
    }

    private UserDTO toDTO(User user) {
        var dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    private User toEntity(UserCreateDTO userDto) {
        var user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    private User toEntityForUpdate(UserUpdateDTO userDto, User user) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO show(@PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id + " Not Found"));
        return toDTO(user);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO create(@RequestBody UserCreateDTO userData) {
        var user = toEntity(userData);
        repository.save(user);
        return toDTO(user);
    }

    @PostMapping("/login")
    String login(@RequestBody AuthRequest authRequest) {
        var authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());
        return jwtUtils.generateToken(authRequest.getUsername());
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO update(@RequestBody UserUpdateDTO data, @PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id + " Not Found"));
        toEntityForUpdate(data, user);
        repository.save(user);
        return toDTO(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroy(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

