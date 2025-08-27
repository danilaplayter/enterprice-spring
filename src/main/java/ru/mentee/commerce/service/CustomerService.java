/* @MENTEE_POWER (C)2025 */
package ru.mentee.commerce.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.commerce.api.dto.CustomerOrderSummaryDto;
import ru.mentee.commerce.domain.model.Customer;
import ru.mentee.commerce.domain.repository.CustomerRepository;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**1 + N если обращаться к orders**/
    public List<Customer> getAllCustomersProblematic() {
        return customerRepository.findAllBasic();
    }

    /**Оптимизированный метод**/
    public List<Customer> getAllCustomersOptimized() {
        return customerRepository.findAllWithOrdersOptimized();
    }

    /**Использование проекции**/
    public List<CustomerOrderSummaryDto> getCustomerSummaries() {
        return customerRepository.findCustomerOrderSummary();
    }
}
