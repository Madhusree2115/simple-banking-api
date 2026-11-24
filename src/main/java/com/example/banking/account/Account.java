package com.example.banking.account;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="bank_accounts")
public class Account {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,length=120)
    private String customerName;
    @Column(nullable=false,precision=19,scale=2)
    private BigDecimal balance;
    @Column(nullable=false,unique=true,length=30)
    private String accountNumber;
    @Column(nullable=false)
    private Instant createdAt=Instant.now();
    @Version
    private Long version;

    protected Account() {}
    public Account(String customerName, String accountNumber, BigDecimal balance) {
        this.customerName=customerName; this.accountNumber=accountNumber; this.balance=balance;
    }
    public Long getId(){return id;}
    public String getCustomerName(){return customerName;}
    public BigDecimal getBalance(){return balance;}
    public String getAccountNumber(){return accountNumber;}
    public Instant getCreatedAt(){return createdAt;}
    public void credit(BigDecimal amount){balance=balance.add(amount);}
    public void debit(BigDecimal amount){balance=balance.subtract(amount);}
}
