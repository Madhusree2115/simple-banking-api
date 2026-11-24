package com.example.banking.api;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class BankingDtos {
    public record CreateAccountRequest(@NotBlank @Size(max=120) String customerName,
        @NotNull @DecimalMin(value="0.00") @Digits(integer=17,fraction=2) BigDecimal initialDeposit) {}
    public record AmountRequest(@NotNull @DecimalMin(value="0.01") @Digits(integer=17,fraction=2) BigDecimal amount) {}
    public record TransferRequest(@NotNull Long fromAccountId, @NotNull Long toAccountId,
        @NotNull @DecimalMin(value="0.01") @Digits(integer=17,fraction=2) BigDecimal amount) {}
    public record AccountResponse(Long id,String customerName,String accountNumber,BigDecimal balance) {}
}
