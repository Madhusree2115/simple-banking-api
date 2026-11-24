package com.example.banking.api;

import com.example.banking.service.BankingService;
import com.example.banking.account.Account;
import com.example.banking.transaction.BankTransaction;
import com.example.banking.api.BankingDtos.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BankingController {
    private final BankingService service;
    public BankingController(BankingService service){this.service=service;}

    @PostMapping("/accounts") @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest req){return dto(service.create(req));}
    @GetMapping("/accounts") public List<AccountResponse> all(){return service.all().stream().map(BankingController::dto).toList();}
    @GetMapping("/accounts/{id}") public AccountResponse get(@PathVariable Long id){return dto(service.get(id));}
    @PostMapping("/accounts/{id}/deposit") public AccountResponse deposit(@PathVariable Long id,@Valid @RequestBody AmountRequest req){return dto(service.deposit(id,req.amount()));}
    @PostMapping("/accounts/{id}/withdraw") public AccountResponse withdraw(@PathVariable Long id,@Valid @RequestBody AmountRequest req){return dto(service.withdraw(id,req.amount()));}
    @PostMapping("/transfers") @ResponseStatus(HttpStatus.CREATED) public String transfer(@Valid @RequestBody TransferRequest req){service.transfer(req);return "Transfer completed";}
    @GetMapping("/accounts/{id}/transactions") public List<BankTransaction> history(@PathVariable Long id){return service.history(id);}
    private static AccountResponse dto(Account a){return new AccountResponse(a.getId(),a.getCustomerName(),a.getAccountNumber(),a.getBalance());}
}
