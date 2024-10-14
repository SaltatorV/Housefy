import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.saltator.housefy")
public class NoteShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteShelfApplication.class, args);
    }
}
