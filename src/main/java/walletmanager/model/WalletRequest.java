package walletmanager.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class WalletRequest {

    private UUID walletId;

    private String operationType;

    private BigDecimal amount;
}
