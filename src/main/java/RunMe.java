import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by ToanDQ on 4/1/2017.
 */

@SpringBootApplication
public class RunMe extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RunMe.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RunMe.class, args);
    }
}
