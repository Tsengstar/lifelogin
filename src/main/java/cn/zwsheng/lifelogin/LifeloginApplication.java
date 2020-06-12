package cn.zwsheng.lifelogin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
@Slf4j
public class LifeloginApplication {

    public static void main(String[] args) {
        String env = System.getProperty("service.env");
        log.debug("启动环境:{}", env);
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, env);

        SpringApplication.run(LifeloginApplication.class, args);
    }

}
