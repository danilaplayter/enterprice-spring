/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.group;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.mentee.api.generated.dto.TransferRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class Transfer extends TransferRequest {

    @Override
    @NotBlank(message = "{transfer.from.required}")
    public String getFromAccount() {
        return super.getFromAccount();
    }

    @Override
    @NotBlank(message = "{transfer.to.required}")
    public String getToAccount() {
        return super.getToAccount();
    }

    @Override
    @DecimalMin(value = "0.01", message = "{amount.min}")
    @DecimalMax(value = "1000000", message = "{amount.max}")
    public BigDecimal getAmount() {
        return super.getAmount();
    }
}
