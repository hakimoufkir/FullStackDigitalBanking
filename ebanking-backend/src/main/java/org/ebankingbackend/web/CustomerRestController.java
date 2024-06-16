package org.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ebankingbackend.dtos.AccountOperationDTO;
import org.ebankingbackend.dtos.CustomerDTO;
import org.ebankingbackend.entitites.Customer;
import org.ebankingbackend.exceptions.CustomerNotFoundException;
import org.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @RequestMapping("/customers")
    public List<CustomerDTO> customer(){
        return bankAccountService.listCustomers();
    }

    @RequestMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name="keyword",defaultValue = "") String keyword ){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }


    @RequestMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody  CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }


    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable(name = "id") Long customerId, @RequestBody  CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Long customerId){
        bankAccountService.deleteCustomer(customerId);
    }


}
