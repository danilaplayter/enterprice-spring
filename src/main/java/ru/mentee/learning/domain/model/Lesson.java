/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.domain.model;

import lombok.Data;

@Data
public class Lesson {
    private String id;
    private String courseId;
    private String videoUrl;
    private String videoTitle;
    private Integer videoDuration;
}
