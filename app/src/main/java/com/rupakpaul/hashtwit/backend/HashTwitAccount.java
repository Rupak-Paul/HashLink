package com.rupakpaul.hashtwit.backend;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.FileId;
import com.rupakpaul.hashtwit.backend.containers.AccountMetadata;
import com.rupakpaul.hashtwit.backend.containers.Profile;
import com.rupakpaul.hashtwit.backend.services.FileService;
import com.rupakpaul.hashtwit.backend.services.TokenService;

public class HashTwitAccount {
    private static final String ACCOUNT_TOKEN_NAME = "HASH_TWIT_ACCOUNT";
    private static final String ACCOUNT_TOKEN_SYMBOL = "HTA";

    public static boolean exist(HederaAccount hederaAccount) throws Exception {
        if(TokenService.getNFTMetadata(hederaAccount, HashTwitAccount.ACCOUNT_TOKEN_NAME, HashTwitAccount.ACCOUNT_TOKEN_SYMBOL) == null) return false;
        else return true;
    }

    public static void create(HederaAccount hederaAccount, Profile profile) throws Exception {
        if(exist(hederaAccount)) return;

        FileId fileIdOfProfile = FileService.uploadNewFile(hederaAccount, profile.serialize());
        AccountMetadata accountMetadata = new AccountMetadata(fileIdOfProfile.toString(), null, null, null, null);
        TokenService.createNewNFT(hederaAccount, HashTwitAccount.ACCOUNT_TOKEN_NAME, HashTwitAccount.ACCOUNT_TOKEN_SYMBOL, accountMetadata.serializeToString());
    }

    public static void update(HederaAccount hederaAccount, Profile profile) throws Exception {
        AccountMetadata accountMetadata = new AccountMetadata(TokenService.getNFTMetadata(hederaAccount, HashTwitAccount.ACCOUNT_TOKEN_NAME, HashTwitAccount.ACCOUNT_TOKEN_SYMBOL));
        FileService.updateFile(hederaAccount, accountMetadata.getFileIdOfProfile(), profile.serialize());
    }

    public static Profile getProfile(HederaAccount hederaAccount) throws Exception {
        return getProfile(hederaAccount, hederaAccount.getAccountId());
    }

    public static Profile getProfile(HederaAccount client, AccountId accountId) throws Exception {
        AccountMetadata accountMetadata = new AccountMetadata(TokenService.getNFTMetadata(client, accountId, HashTwitAccount.ACCOUNT_TOKEN_NAME, HashTwitAccount.ACCOUNT_TOKEN_SYMBOL));
        FileId fileIdOfProfile = accountMetadata.getFileIdOfProfile();
        return new Profile(FileService.getFileContent(client, fileIdOfProfile));
    }
}