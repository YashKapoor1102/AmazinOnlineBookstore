package amazin.bookstore;

import amazin.bookstore.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AccessingDataJpaApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            String adminUsername = System.getenv("ADMIN_USERNAME");
            String adminPassword = System.getenv("ADMIN_PASSWORD");

            // Command prompt on Windows:
            // setx ADMIN_USERNAME "your_admin_username"
            // setx ADMIN_PASSWORD "your_admin_password"

            // JAR file MUST be ran from command prompt as well
            // Use this command after setting env vars: java -jar AmazinOnlineBookstore-0.0.1-SNAPSHOT.jar

            // Or you can go to Run -> Edit Configurations and set the env vars there
            // like this: ADMIN_USERNAME=username;ADMIN_PASSWORD=password

            // Terminal on MacOS:
            // export ADMIN_USERNAME="your_admin_username"
            // export ADMIN_PASSWORD="your_admin_username"

            // Run these two commands before running the JAR file

            if (adminUsername == null || adminPassword == null) {
                log.error("Environment variables for ADMIN_USERNAME and/or ADMIN_PASSWORD are not set.");
                return; // Exit the CommandLineRunner if the required environment variables are not set
            }

            User user = new User(adminUsername, adminPassword, true);

            userRepository.save(user);

            for(User us : userRepository.findAll()) {
                log.info(us.toString());
            }
        };
    }
}