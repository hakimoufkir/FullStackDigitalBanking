package org.ebankingbackend;

import org.ebankingbackend.dtos.BankAccountDTO;
import org.ebankingbackend.dtos.CurrentBankAccountDTO;
import org.ebankingbackend.dtos.CustomerDTO;
import org.ebankingbackend.dtos.SavingBankAccountDTO;
import org.ebankingbackend.entitites.*;
import org.ebankingbackend.enums.AccountStatus;
import org.ebankingbackend.enums.OperationType;
import org.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.ebankingbackend.exceptions.CustomerNotFoundException;
import org.ebankingbackend.repositories.AccountOperationRepository;
import org.ebankingbackend.repositories.BankAccountRepository;
import org.ebankingbackend.repositories.CustomerRepository;
import org.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan", "Yassine", "Aicha").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 90000, 5.5, customer.getId());

                }catch (CustomerNotFoundException e){
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts =bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    }else{
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }
            }
        };
    }
//    @Bean
    CommandLineRunner start(
            CustomerRepository customerRepository,
            BankAccountRepository bankAccountRepository,
            AccountOperationRepository accountOperationRepository){
        return args -> {
            // random clients
            Stream.of("Hassan", "Yassine", "Aicha").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            // create foreach client bankAccount
            customerRepository.findAll().forEach(c->{
                CurrentAccount currentAccount = new CurrentAccount();

                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                currentAccount.setBalance(Math.random()*9000);

                currentAccount.setCustomer(c);
                bankAccountRepository.save(currentAccount);


                SavingAccount savingAccount = new SavingAccount();

                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(5.5);
                savingAccount.setBalance(Math.random()*9000);

                savingAccount.setCustomer(c);
                bankAccountRepository.save(savingAccount);
            });
            // each bankaccount do some operations
            bankAccountRepository.findAll().forEach(b->{
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5 ?
                            OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(b);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
