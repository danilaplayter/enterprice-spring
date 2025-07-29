/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OnlineSchoolMain {
    public static void main(String[] args) {
        SpringApplication.run(OnlineSchoolMain.class, args);
    }
}
