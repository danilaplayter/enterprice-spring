/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import ru.mentee.library.service.audit.OperationAuditLogger;

public class ApplicationConfig {
    @Bean
    @Scope("prototype")
    public OperationAuditLogger operationAuditLogger() {
        return new OperationAuditLogger();
    }
}
