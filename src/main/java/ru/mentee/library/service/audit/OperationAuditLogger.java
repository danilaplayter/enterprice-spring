/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.service.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OperationAuditLogger {

    private static final Logger logger = LoggerFactory.getLogger(OperationAuditLogger.class);

    public void log(String operation, String username) {
        logger.info("User '{}' performed operation: {}", username, operation);
    }
}
