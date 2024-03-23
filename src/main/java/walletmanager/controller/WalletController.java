package walletmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import walletmanager.WalletRequest;
import walletmanager.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/wallet")
public class WalletController {

    private final WalletService service;

    @Autowired
    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> updateBalance(@RequestBody WalletRequest request) {
        service.updateBalance(request.getWalletId(), request.getAmount(), request.getOperationType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID walletId) {
        BigDecimal balance = service.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }
}
