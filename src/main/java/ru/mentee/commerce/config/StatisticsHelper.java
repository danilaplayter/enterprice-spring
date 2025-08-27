/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.config;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Component;

@Component
public class StatisticsHelper {

    private final SessionFactory sessionFactory;

    public StatisticsHelper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void clearStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        stats.clear();
    }

    public long getQueryCount() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getQueryExecutionCount();
    }

    public long getPreparedStatementCount() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getPrepareStatementCount();
    }

    public void logStatistics(String operationName) {
        Statistics stats = sessionFactory.getStatistics();
        System.out.println("=====" + operationName + " Statistics =====");
        System.out.println("Queries executed: " + stats.getQueryExecutionCount());
        System.out.println("Statements prepared: " + stats.getPrepareStatementCount());
        System.out.println("Entities loaded: " + stats.getEntityLoadCount());
        System.out.println("Collections loaded: " + stats.getCollectionLoadCount());
        System.out.println("Second level cache hits: " + stats.getSecondLevelCacheHitCount());
        System.out.println("Second level cache misses: " + stats.getSecondLevelCacheMissCount());
        System.out.println("=====================================");
    }
}
