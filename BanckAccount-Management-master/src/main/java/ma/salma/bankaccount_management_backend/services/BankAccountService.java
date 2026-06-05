//Fonctionnalités :Création de clients/Création de comptes /Débit / Crédit/ Virement/ Consultation historique des opérations
//C’est le cœur du projet.

package ma.salma.bankaccount_management_backend.services;

import java.util.List;

import ma.salma.bankaccount_management_backend.dtos.*;
import ma.salma.bankaccount_management_backend.exceptions.BalanceNotSufficientException;
import ma.salma.bankaccount_management_backend.exceptions.BankAccountNotFoundEcxeption;
import ma.salma.bankaccount_management_backend.exceptions.CustomerNotFoundEcxeption;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundEcxeption;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundEcxeption;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundEcxeption;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundEcxeption, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundEcxeption;
    void transfer(String accountIdSource, String accountIdDest, double amount) throws BalanceNotSufficientException, BankAccountNotFoundEcxeption;


    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomerDTO(Long customerId) throws CustomerNotFoundEcxeption;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundEcxeption;

    List<CustomerDTO> searchCustomers(String keyword);

}
