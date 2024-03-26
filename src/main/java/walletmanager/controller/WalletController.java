package walletmanager.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import walletmanager.model.WalletRequest;
import walletmanager.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class WalletController {

    private final WalletService service;

    @Autowired
    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping("api/v1/wallet")
    public ResponseEntity<Void> updateBalance(@Valid @RequestBody WalletRequest request) {
        service.updateBalance(request.getWalletId(), request.getAmount(), request.getOperationType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/v1/wallet/{walletId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID walletId) {
        BigDecimal balance = service.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }
}
