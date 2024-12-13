package com.rupakpaul.hashtwit.backend;

import com.hedera.hashgraph.sdk.AccountInfoQuery;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;

public class HederaAccount {
    private String accountId;
    private String privateKey;

    public HederaAccount(String accountId, String privateKey) throws Exception {
        if(!HederaAccount.valid(accountId, privateKey)) {
            throw new Exception("Unable to validate Account Id and Private Key!");
        }
        else {
            this.accountId = accountId;
            this.privateKey = privateKey;
        }
    }

    public static boolean valid(String accountId, String privateKey) {
        try {
            Client client = Client.forTestnet();
            client.setOperator(AccountId.fromString(accountId), PrivateKey.fromString((privateKey)));

            new AccountInfoQuery()
                    .setAccountId(AccountId.fromString(accountId))
                    .execute(client);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Client getClient() {
        Client client = Client.forTestnet();
        client.setOperator(AccountId.fromString(accountId), PrivateKey.fromString(privateKey));
        return client;
    }

    public PrivateKey getPrivateKey() {
        return PrivateKey.fromString(this.privateKey);
    }

    public PublicKey getPublicKey() {
        return getPrivateKey().getPublicKey();
    }

    public AccountId getAccountId() {
        return AccountId.fromString(accountId);
    }
}