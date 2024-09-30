package hexlet.code.controller;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    public UserRepository repository;

    @GetMapping("/temp2")
    public String index2() {
        return "labuda2";
    }

    @GetMapping("/users")
    List<User> index() {
        var users = repository.findAll();
        return users.stream().toList();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User create(@RequestBody User user) {
        repository.save(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroy(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

