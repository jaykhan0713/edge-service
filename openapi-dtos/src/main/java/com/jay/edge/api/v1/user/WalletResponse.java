package com.jay.edge.api.v1.user;

public record WalletResponse(
        int paidTokens,
        int freeTokens,
        int totalTokens
) {}
