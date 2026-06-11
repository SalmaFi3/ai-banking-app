package ma.salma.bankaccount_management_backend.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentBankAccountDTO extends BankAccountDTO {
    private String id;
    private Date createdAt;
    private double balance;
    private String currency;
    private CustomerDTO customerDTO;
    private double overdraft;

}
