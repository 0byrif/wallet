package walletmanager.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import walletmanager.model.Wallet;
import walletmanager.exception.InsufficientBalanceException;
import walletmanager.exception.WalletNotFoundException;
import walletmanager.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository repository;

    @Autowired
    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateBalance(@NotNull UUID walletId, @Positive BigDecimal amount,
                              @NotBlank String operationType) {

        Wallet wallet = repository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found" + walletId));

        if (operationType.equals("WITHDRAW")) {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new InsufficientBalanceException("Insufficient balance in the wallet");
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
        } else if (operationType.equals("DEPOSIT")) {
            wallet.setBalance(wallet.getBalance().add(amount));
        }
        repository.save(wallet);
    }

    public BigDecimal getBalance(@NotNull UUID walletId) {
        Wallet wallet = repository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found" + walletId));
        return wallet.getBalance();
    }
}
