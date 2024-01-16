package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.services.UsersService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);
        UsersService service = context.getBean(
                "userService", UsersService.class);
        System.out.println(service.signUp("heisyenberg@gmail.com"));
    }
}
