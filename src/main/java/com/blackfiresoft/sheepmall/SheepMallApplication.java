package com.blackfiresoft.sheepmall;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class SheepMallApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(SheepMallApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
