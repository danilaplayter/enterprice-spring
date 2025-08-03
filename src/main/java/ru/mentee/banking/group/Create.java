/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.group;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.mentee.api.generated.dto.CreateAccountRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class Create extends CreateAccountRequest {

    @Override
    @NotBlank(message = "{account.type.required}")
    @Pattern(regexp = "CHECKING|SAVINGS|DEPOSIT", message = "{account.type.invalid}")
    public AccountTypeEnum getAccountType() {
        return super.getAccountType();
    }

    @Override
    @NotBlank(message = "{currency.required}")
    @Size(min = 3, max = 3, message = "{currency.size}")
    public String getCurrency() {
        return super.getCurrency();
    }

    @Override
    @DecimalMin(value = "0.0", message = "{initial.deposit.min}")
    public BigDecimal getInitialDeposit() {
        return super.getInitialDeposit();
    }
}
