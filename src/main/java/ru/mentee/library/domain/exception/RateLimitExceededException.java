/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.exception;

public class RateLimitExceededException extends RuntimeException {
    private final int remaining;
    private final long resetTime;

    public RateLimitExceededException(int remaining, long resetTime) {
        super("Rate limit exceeded");
        this.remaining = remaining;
        this.resetTime = resetTime;
    }

    public int getRemaining() {
        return remaining;
    }

    public long getResetTime() {
        return resetTime;
    }
}
