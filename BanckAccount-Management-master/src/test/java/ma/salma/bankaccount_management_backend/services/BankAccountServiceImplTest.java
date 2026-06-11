package ma.salma.bankaccount_management_backend.services;

// Tests unitaires écrits par Gemini CLI — Role Testing Lead

import ma.salma.bankaccount_management_backend.dtos.CurrentBankAccountDTO;
import ma.salma.bankaccount_management_backend.dtos.CustomerDTO;
import ma.salma.bankaccount_management_backend.dtos.BankAccountDTO;
import ma.salma.bankaccount_management_backend.exceptions.BalanceNotSufficientException;
import ma.salma.bankaccount_management_backend.exceptions.BankAccountNotFoundEcxeption;
import ma.salma.bankaccount_management_backend.exceptions.CustomerNotFoundEcxeption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BankAccountServiceImplTest {

    @Autowired
    private BankAccountService bankAccountService;

    // Test 1 — Créer un client et vérifier qu'il est sauvegardé
    @Test
    public void testSaveCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Doha Test");
        customerDTO.setEmail("doha@test.com");

        CustomerDTO saved = bankAccountService.saveCustomer(customerDTO);

        assertNotNull(saved);
        assertEquals("Doha Test", saved.getName());
        assertEquals("doha@test.com", saved.getEmail());
    }

    // Test 2 — Créer un compte courant pour un client existant
    @Test
    public void testSaveCurrentBankAccount() throws CustomerNotFoundEcxeption {
        // D'abord créer un client
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Test Client");
        customerDTO.setEmail("test@bank.com");
        CustomerDTO savedCustomer = bankAccountService.saveCustomer(customerDTO);

        // Créer un compte courant
        CurrentBankAccountDTO account = bankAccountService.saveCurrentBankAccount(
            1000.0, 500.0, savedCustomer.getId()
        );

        assertNotNull(account);
        assertEquals(1000.0, account.getBalance());
    }

    // Test 3 — Vérifier que le débit fonctionne correctement
    @Test
    public void testDebitSuccess() throws CustomerNotFoundEcxeption, BankAccountNotFoundEcxeption, BalanceNotSufficientException {
        // Créer client et compte
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Debit Test");
        customerDTO.setEmail("debit@test.com");
        CustomerDTO savedCustomer = bankAccountService.saveCustomer(customerDTO);

        CurrentBankAccountDTO account = bankAccountService.saveCurrentBankAccount(
            2000.0, 500.0, savedCustomer.getId()
        );

        // Effectuer un débit
        bankAccountService.debit(account.getId(), 500.0, "Test debit");

        // Vérifier le nouveau solde
        BankAccountDTO updatedAccount = bankAccountService.getBankAccount(account.getId());
        if (updatedAccount instanceof CurrentBankAccountDTO) {
            assertEquals(1500.0, ((CurrentBankAccountDTO) updatedAccount).getBalance());
        } else if (updatedAccount instanceof ma.salma.bankaccount_management_backend.dtos.SavingBankAccountDTO) {
            assertEquals(1500.0, ((ma.salma.bankaccount_management_backend.dtos.SavingBankAccountDTO) updatedAccount).getBalance());
        } else {
            fail("Unexpected account type");
        }
    }

    // Test 4 — Vérifier qu'un débit avec solde insuffisant lève une exception
    @Test
    public void testDebitInsufficientBalance() throws CustomerNotFoundEcxeption, BankAccountNotFoundEcxeption {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Insufficient Test");
        customerDTO.setEmail("insufficient@test.com");
        CustomerDTO savedCustomer = bankAccountService.saveCustomer(customerDTO);

        CurrentBankAccountDTO account = bankAccountService.saveCurrentBankAccount(
            100.0, 0.0, savedCustomer.getId()
        );

        // Tenter un débit supérieur au solde — doit lancer une exception
        assertThrows(BalanceNotSufficientException.class, () -> {
            bankAccountService.debit(account.getId(), 9999.0, "Test trop grand");
        });
    }

    // Test 5 — Vérifier que la liste des clients n'est pas vide
    @Test
    public void testListCustomers() {
        // Ajouter un client
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("List Test");
        customerDTO.setEmail("list@test.com");
        bankAccountService.saveCustomer(customerDTO);

        // Vérifier que la liste n'est pas vide
        var customers = bankAccountService.listCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }
}
