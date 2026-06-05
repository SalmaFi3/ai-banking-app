package ma.salma.bankaccount_management_backend.dtos;

//import org.springframework.web.bind.annotation.PostMapping;

import lombok.*;
//import ma.youssef.bankaccount_management_backend.entities.Customer;

@Data

public class CustomerDTO  {
    private Long id;
    private String name;
    private String email;

    
}
