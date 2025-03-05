package com.simulacro.bank.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currentAccounts")
@DiscriminatorValue("savingAccount")
public class SavingsAccount extends Account {

    private double yieldRate;

}
