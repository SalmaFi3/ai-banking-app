//Fournie des endpoints REST accessibles via HTTP 

package ma.salma.bankaccount_management_backend.web;

import org.springframework.web.bind.annotation.*;

import ma.salma.bankaccount_management_backend.dtos.AccountHistoryDTO;
import ma.salma.bankaccount_management_backend.dtos.AccountOperationDTO;
import ma.salma.bankaccount_management_backend.dtos.BankAccountDTO;
import ma.salma.bankaccount_management_backend.exceptions.BankAccountNotFoundEcxeption;
import ma.salma.bankaccount_management_backend.services.BankAccountService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BankAccountRestAPI {

    private final BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundEcxeption {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundEcxeption {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }

    // Endpoint ajouté par Doha — recherche de comptes par solde minimum
    @GetMapping("/accounts/search")
    public List<BankAccountDTO> searchAccountsByMinBalance(
            @RequestParam(name = "minBalance", defaultValue = "0") double minBalance) {
        return bankAccountService.bankAccountList()
                .stream()
                .filter(acc -> acc.getBalance() >= minBalance)
                .collect(Collectors.toList());
    }
}