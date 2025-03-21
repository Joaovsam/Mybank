package com.simulacro.bank.mapper;

import com.simulacro.bank.DTO.AccountDTO;
import com.simulacro.bank.model.Account;
import com.simulacro.bank.model.AccountType;
import com.simulacro.bank.model.CurrentAccount;
import com.simulacro.bank.model.SavingsAccount;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", imports = {CurrentAccount.class, SavingsAccount.class})
public interface AccountMapper {

    @Mapping(target = "customerId",expression = "java(account.getCustomer().getId())")
    @Mapping(target = "accountType", expression = "java(mapAccountType(account instanceof CurrentAccount ? \"CURRENT\" : \"SAVINGS\"))")
    @Mapping(target = "creditLimit", expression = "java(account instanceof CurrentAccount ? ((CurrentAccount) account).getCreditLimit() : 0)")
    @Mapping(target = "yieldRate", expression = "java(account instanceof SavingsAccount ? ((SavingsAccount) account).getYieldRate() : 0)")
    AccountDTO accountToAccountDto(Account account);

    default Page<AccountDTO> accounsToAccountDto(Page<Account> accounts) {
        return accounts.map(this::accountToAccountDto);
    }
    
    default AccountType mapAccountType(String accountType) {
    if (accountType == null) {
        return null;
    }
    return AccountType.valueOf(accountType.toUpperCase());
}

    List<AccountDTO> accountListToAccountDtoList(List<Account> accounts);

    default void accountDtoToAccount(AccountDTO accountDto, @MappingTarget Account account) {
        if (accountDto.getNumberAccount() != null) {
            account.setNumberAccount(accountDto.getNumberAccount());
        }
        if (accountDto.getAgency() != 0) {
            account.setAgency(accountDto.getAgency());
        }
        if (accountDto.getBalance() != null) {
            account.setBalance(accountDto.getBalance());
        }

        if (account instanceof CurrentAccount && accountDto.getCreditLimit() != 0) {
            ((CurrentAccount) account).setCreditLimit(accountDto.getCreditLimit());
        }

        if (account instanceof SavingsAccount && accountDto.getYieldRate() != 0) {
            ((SavingsAccount) account).setYieldRate(accountDto.getYieldRate());
        }
    }

}
