/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@EnableConfigurationProperties
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        SessionFactory sessionFactory = event.getApplicationContext().getBean(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
    }
}
