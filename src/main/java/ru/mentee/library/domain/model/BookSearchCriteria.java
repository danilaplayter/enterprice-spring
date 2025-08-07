/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchCriteria {
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String categoryName;
    private Integer publicationYearFrom;
    private Integer publicationYearTo;
    private Integer pagesFrom;
    private Integer pagesTo;
    private Boolean availableOnly;
    private Pageable pageable;
}
