package hexlet.code.service;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserUtils userUtils;

    public List<User> getAll() {
        var users = repository.findAll();
        var result = users.stream()
                .toList();
        return result;
    }


}
