package ma.salma.bankaccount_management_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ma.salma.bankaccount_management_backend.dtos.BankAccountDTO;
import ma.salma.bankaccount_management_backend.dtos.CurrentBankAccountDTO;
import ma.salma.bankaccount_management_backend.dtos.CustomerDTO;
import ma.salma.bankaccount_management_backend.dtos.SavingBankAccountDTO;
import ma.salma.bankaccount_management_backend.entities.*;
import ma.salma.bankaccount_management_backend.enums.AccountStatus;
import ma.salma.bankaccount_management_backend.enums.OperationType;
import ma.salma.bankaccount_management_backend.exceptions.CustomerNotFoundException;
import ma.salma.bankaccount_management_backend.repositories.AccountOperationRepository;
import ma.salma.bankaccount_management_backend.repositories.BankAccountRepository;
import ma.salma.bankaccount_management_backend.repositories.CustomerRepository;
import ma.salma.bankaccount_management_backend.services.BankAccountService;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankAccountManagementBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountManagementBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
          Stream.of("Salma", "Doha" , "Hafssa","Wissal").forEach(name -> {
              CustomerDTO customerDTO = new CustomerDTO();
              customerDTO.setName(name);
              customerDTO.setEmail(name+"123@gmail.com");
              bankAccountService.saveCustomer(customerDTO);
          });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 80000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount : bankAccounts) {
                for (int i = 0; i < 10 ; i++) {
                    String accountId;
                    if (bankAccount instanceof CurrentBankAccountDTO){
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    } else {
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000+Math.random()*20000, "Credit");
                    bankAccountService.debit(accountId, 10000+Math.random()*8000, "Debit");

                }
            }
        };
    }

}
