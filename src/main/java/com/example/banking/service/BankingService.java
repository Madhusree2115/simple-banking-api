package com.example.banking.service;

import com.example.banking.account.*;
import com.example.banking.transaction.*;
import com.example.banking.api.BankingDtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BankingService {
    private final AccountRepository accounts;
    private final TransactionRepository transactions;
    public BankingService(AccountRepository accounts, TransactionRepository transactions){
        this.accounts=accounts; this.transactions=transactions;
    }
    @Transactional
    public Account create(CreateAccountRequest request){
        return accounts.save(new Account(request.customerName(),newAccountNumber(),request.initialDeposit()));
    }
    @Transactional(readOnly=true)
    public List<Account> all(){return accounts.findAll();}
    @Transactional(readOnly=true)
    public Account get(Long id){return find(id);}
    @Transactional
    public Account deposit(Long id, BigDecimal amount){
        validateAmount(amount); Account account=find(id); account.credit(amount);
        accounts.save(account); transactions.save(new BankTransaction(null,id,amount,"DEPOSIT")); return account;
    }
    @Transactional
    public Account withdraw(Long id, BigDecimal amount){
        validateAmount(amount); Account account=find(id);
        if(account.getBalance().compareTo(amount)<0) throw new BankingException("Insufficient balance");
        account.debit(amount); accounts.save(account);
        transactions.save(new BankTransaction(id,null,amount,"WITHDRAWAL")); return account;
    }
    @Transactional
    public void transfer(TransferRequest request){
        validateAmount(request.amount());
        if(request.fromAccountId().equals(request.toAccountId())) throw new BankingException("Cannot transfer to the same account");
        Account from=find(request.fromAccountId()); Account to=find(request.toAccountId());
        if(from.getBalance().compareTo(request.amount())<0) throw new BankingException("Insufficient balance");
        from.debit(request.amount()); to.credit(request.amount());
        accounts.save(from); accounts.save(to);
        transactions.save(new BankTransaction(from.getId(),to.getId(),request.amount(),"TRANSFER"));
    }
    @Transactional(readOnly=true)
    public List<BankTransaction> history(Long id){find(id); return transactions.findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(id,id);}
    private Account find(Long id){return accounts.findById(id).orElseThrow(()->new BankingException("Account not found: "+id));}
    private void validateAmount(BigDecimal amount){if(amount==null||amount.compareTo(BigDecimal.ZERO)<=0) throw new BankingException("Amount must be greater than zero");}
    private String newAccountNumber(){return "AC"+UUID.randomUUID().toString().replace("-","").substring(0,14).toUpperCase();}
}
