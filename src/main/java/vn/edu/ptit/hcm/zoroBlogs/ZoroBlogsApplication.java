package vn.edu.ptit.hcm.zoroBlogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ZoroBlogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZoroBlogsApplication.class, args);
        
    }

}
