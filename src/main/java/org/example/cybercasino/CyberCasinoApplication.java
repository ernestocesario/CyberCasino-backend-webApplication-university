package org.example.cybercasino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CyberCasinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyberCasinoApplication.class, args);
    }

}
