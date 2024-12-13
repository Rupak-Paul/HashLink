package com.rupakpaul.hashtwit.backend.services;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.AccountInfo;
import com.hedera.hashgraph.sdk.AccountInfoQuery;
import com.hedera.hashgraph.sdk.TokenCreateTransaction;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenInfo;
import com.hedera.hashgraph.sdk.TokenInfoQuery;
import com.hedera.hashgraph.sdk.TokenMintTransaction;
import com.hedera.hashgraph.sdk.TokenSupplyType;
import com.hedera.hashgraph.sdk.TokenType;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.rupakpaul.hashtwit.backend.HederaAccount;

public class TokenService {
    public static TokenId createNewNFT(HederaAccount hederaAccount, String tokenName, String tokenSymbol, String metadata) throws Exception {
        TransactionReceipt receipt = new TokenCreateTransaction()
                .setTokenName(tokenName)
                .setTokenType(TokenType.NON_FUNGIBLE_UNIQUE)
                .setTokenSymbol(tokenSymbol)
                .setInitialSupply(0)
                .setTreasuryAccountId(hederaAccount.getAccountId())
                .setAdminKey(hederaAccount.getPublicKey())
                .setSupplyKey(hederaAccount.getPublicKey())
                .setSupplyType(TokenSupplyType.FINITE)
                .setMaxSupply(1)
                .setTokenMemo(metadata)
                .execute(hederaAccount.getClient())
                .getReceipt(hederaAccount.getClient());

        new TokenMintTransaction()
                .setTokenId(receipt.tokenId)
                .addMetadata(metadata.getBytes())
                .execute(hederaAccount.getClient())
                .getReceipt(hederaAccount.getClient());

        return receipt.tokenId;
    }

    public static String getNFTMetadata(HederaAccount hederaAccount, String tokenName, String tokenSymbol) throws Exception {
        return getNFTMetadata(hederaAccount, hederaAccount.getAccountId(), tokenName, tokenSymbol);
    }

    public static String getNFTMetadata(HederaAccount client, AccountId hederaAccountId, String tokenName, String tokenSymbol) throws Exception {
        AccountInfo accountInfo = new AccountInfoQuery()
                .setAccountId(hederaAccountId)
                .execute(client.getClient());

        for(TokenId tokenId : accountInfo.tokenRelationships.keySet()) {
            TokenInfo tokenInfo = new TokenInfoQuery()
                    .setTokenId(tokenId)
                    .execute(client.getClient());

            if(tokenInfo.tokenType != TokenType.NON_FUNGIBLE_UNIQUE) continue;
            if(!tokenInfo.name.equals(tokenName)) continue;
            if(!tokenInfo.symbol.equals(tokenSymbol)) continue;
            if(tokenInfo.maxSupply != 1) continue;
            if(tokenInfo.totalSupply != 1) continue;

            return tokenInfo.tokenMemo;
        }

        return null;
    }
}