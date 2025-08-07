/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mentee.library.domain.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>, CustomLoanRepository {
    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId AND l.returnDate IS NULL")
    List<Loan> findActiveLoansByUser(@Param("userId") Long userId);

    @Query("SELECT l FROM Loan l WHERE l.book.id = :bookId ORDER BY l.loanDate DESC")
    List<Loan> findLoanHistoryByBook(@Param("bookId") Long bookId);

    @Query("SELECT l FROM Loan l WHERE " + "l.returnDate IS NULL AND l.dueDate < CURRENT_TIMESTAMP")
    List<Loan> findOverdueLoans();
}
