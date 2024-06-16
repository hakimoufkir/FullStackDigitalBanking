package org.ebankingbackend.dtos;

import lombok.Data;
import org.ebankingbackend.entitites.Customer;
import org.ebankingbackend.enums.AccountStatus;

import java.util.Date;
@Data
public class BankAccountDTO {
    private String type;
}
