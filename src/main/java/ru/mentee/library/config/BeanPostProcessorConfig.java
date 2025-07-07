/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeanPostProcessorConfig implements BeanPostProcessor {

    /**
     * Логирует создание бинов перед инициализацией.
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        log.debug("Creating bean: {}", beanName);
        return bean;
    }

    /**
     * Логирует успешную инициализацию бинов.
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        log.debug("Bean initialized: {}", beanName);
        return bean;
    }
}
