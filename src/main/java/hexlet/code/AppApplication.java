package hexlet.code;

import hexlet.code.model.User;
import io.sentry.spring.jakarta.EnableSentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import io.sentry.Sentry;


@SpringBootApplication
//@EnableSentry(dsn = "https://de15f0200e74483d0f73317b25474d44@o4508268179619840.ingest.de.sentry.io/4508269120389200")
@EnableJpaAuditing
@RestController
@RequestMapping("/api")
public class AppApplication {
	private static final List<User> users = new ArrayList<>();

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
