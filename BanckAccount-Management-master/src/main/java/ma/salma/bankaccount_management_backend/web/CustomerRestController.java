//Fournie des endpoints REST accessibles via HTTP 

package ma.salma.bankaccount_management_backend.web;

import lombok.AllArgsConstructor;
import ma.salma.bankaccount_management_backend.dtos.CustomerDTO;
import ma.salma.bankaccount_management_backend.exceptions.CustomerNotFoundException;
import ma.salma.bankaccount_management_backend.services.BankAccountService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers(){
        return bankAccountService.listCustomers();
    }

    @GetMapping("customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomerDTO(customerId);
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword") String keyword) {
        return bankAccountService.searchCustomers(keyword);
    }


    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerDTO.setId( customerId );
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(customerId);
    }





}
