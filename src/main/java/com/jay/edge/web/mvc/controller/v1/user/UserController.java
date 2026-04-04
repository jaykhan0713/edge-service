package com.jay.edge.web.mvc.controller.v1.user;

import com.jay.edge.api.v1.user.WalletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/wallet")
    public WalletResponse getWallet() {
        int paidTokens = 1000;
        int freeTokens = 100;
        int totalTokens = paidTokens + freeTokens;
        return new WalletResponse(1000, 100, totalTokens);
    }
}
