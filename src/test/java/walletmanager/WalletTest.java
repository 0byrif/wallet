package walletmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import walletmanager.controller.WalletController;
import walletmanager.model.WalletRequest;
import walletmanager.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
public class WalletTest {

    @InjectMocks
    private WalletController controller;

    @Mock
    private WalletService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testUpdateBalance() throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("1000");
        String operationType = "DEPOSIT";

        WalletRequest request = new WalletRequest();
        request.setWalletId(walletId);
        request.setAmount(amount);
        request.setOperationType(operationType);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                        .andExpect(status().isOk());

        verify(service, times(1)).updateBalance(walletId, amount, operationType);
    }

    @Test
    public void testGetBalance() throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal balance = new BigDecimal("1000");

        when(service.getBalance(walletId)).thenReturn(balance);

        mockMvc.perform(get("/api/v1/wallet/" + walletId)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(om.writeValueAsString(balance)));

        verify(service, times(1)).getBalance(walletId);
    }
}
