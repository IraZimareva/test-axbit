package zimareva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.logging.Logger;

@SpringBootApplication
public class TestAxbitApplication {

    private static Logger logger = Logger.getLogger(TestAxbitApplication.class.getName());

    public static void main(String[] args) {
        //http://127.0.0.1:6051/browser/ - консоль СУБД Postgre

        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(TestAxbitApplication.class, args);

//        MainService mainService =
//                configurableApplicationContext.getBean(MainService.class);

        logger.info("Go apps");
    }
}
