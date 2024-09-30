package hexlet.code;

import hexlet.code.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class AppApplication {
	private static final List<User> users = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
		User testUser = new User();
		var currentTime = new Timestamp(System.currentTimeMillis());
		testUser.setId(1L);
		testUser.setFirstName("Jack");
		testUser.setLastName("Jones");
		testUser.setEmail("hexlet@example.com");
		testUser.setPassword("qwerty");
		testUser.setCreatedAt(currentTime);
		users.add(testUser);
	}

	@GetMapping("/welcome")
	String home() {
		return "Welcome to Spring";
	}

}
