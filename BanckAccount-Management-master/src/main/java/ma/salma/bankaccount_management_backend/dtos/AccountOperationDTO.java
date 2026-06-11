package ma.salma.bankaccount_management_backend.dtos;

//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
import lombok.Data;
import ma.salma.bankaccount_management_backend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
