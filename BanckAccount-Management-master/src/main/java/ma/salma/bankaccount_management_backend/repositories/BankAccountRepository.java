package ma.salma.bankaccount_management_backend.repositories;

//import ma.youssef.bankaccount_management_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import ma.salma.bankaccount_management_backend.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
