package ma.salma.bankaccount_management_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ma.salma.bankaccount_management_backend.entities.AccountOperation;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccountId(String accountId);
    Page findByBankAccountId(String accountId, Pageable pageable);
}
