/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.group;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.mentee.api.generated.dto.UpdateTaskRequest;

public class Update extends UpdateTaskRequest {

    @Override
    @Pattern(regexp = "ACTIVE|BLOCKED|CLOSED", message = "{status.invalid}")
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    @Override
    @Size(max = 255, message = "{description.size}")
    public String getDescription() {
        return super.getDescription();
    }
}
