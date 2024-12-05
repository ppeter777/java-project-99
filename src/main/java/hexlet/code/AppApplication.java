package hexlet.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.sentry.Sentry;

@SpringBootApplication
@EnableJpaAuditing
@RestController
@RequestMapping("/api")
public class AppApplication {
public static void main(String[] args) {
		try {
			throw new Exception("This is a second test.");
		} catch (Exception e) {
			Sentry.captureException(e);
		}
		SpringApplication.run(AppApplication.class, args);
	}
	@GetMapping("/welcome")
	String home() {
		return "Welcome to Spring";
	}
}
