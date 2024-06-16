package org.ebankingbackend.repositories;

import org.ebankingbackend.entitites.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
