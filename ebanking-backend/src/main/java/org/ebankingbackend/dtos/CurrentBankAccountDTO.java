package org.ebankingbackend.dtos;

import lombok.Data;
import org.ebankingbackend.enums.AccountStatus;

import java.util.Date;
@Data
public class CurrentBankAccountDTO extends BankAccountDTO{
    private String id;
    private Date createdAt;
    private double balance;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private  double overDraft;

}
