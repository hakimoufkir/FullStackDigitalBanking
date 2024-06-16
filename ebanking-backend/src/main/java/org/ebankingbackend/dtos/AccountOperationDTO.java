package org.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ebankingbackend.entitites.BankAccount;
import org.ebankingbackend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private  Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
