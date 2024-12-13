package com.rupakpaul.hashtwit.backend.services;

import com.google.protobuf.ByteString;
import com.hedera.hashgraph.sdk.FileAppendTransaction;
import com.hedera.hashgraph.sdk.FileContentsQuery;
import com.hedera.hashgraph.sdk.FileCreateTransaction;
import com.hedera.hashgraph.sdk.FileId;
import com.hedera.hashgraph.sdk.FileUpdateTransaction;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.rupakpaul.hashtwit.backend.HederaAccount;

import java.util.Arrays;

public class FileService {
    public static FileId uploadNewFile(HederaAccount hederaAccount, byte[] content) throws Exception {
        int maxChunkSize = 5120;
        FileId fileId = null;

        for(int i = 0; i < content.length; i += maxChunkSize) {
            int from = i;
            int to = (i+maxChunkSize > content.length ? content.length : i+maxChunkSize);
            byte[] chunk = Arrays.copyOfRange(content, from, to);

            if(i == 0) {
                TransactionReceipt receipt = new FileCreateTransaction()
                        .setKeys(hederaAccount.getPublicKey())
                        .setContents(chunk)
                        .setMaxTransactionFee(new Hbar(3))
                        .execute(hederaAccount.getClient())
                        .getReceipt(hederaAccount.getClient());

                fileId = receipt.fileId;
            }
            else {
                TransactionReceipt receipt = new FileAppendTransaction()
                        .setFileId(fileId)
                        .setContents(chunk)
                        .setMaxTransactionFee(new Hbar(3))
                        .execute(hederaAccount.getClient())
                        .getReceipt(hederaAccount.getClient());
            }
        }

        return fileId;
    }

    public static void updateFile(HederaAccount hederaAccount, FileId fileId, byte[] content) throws Exception {
        int maxChunkSize = 5120;

        TransactionReceipt truncateReceipt = new FileUpdateTransaction()
                .setFileId(fileId)
                .setContents(new byte[0])  // Truncate by setting the file content to an empty byte array
                .setMaxTransactionFee(new Hbar(3))
                .execute(hederaAccount.getClient())
                .getReceipt(hederaAccount.getClient());

        for(int i = 0; i < content.length; i += maxChunkSize) {
            int from = i;
            int to = (i+maxChunkSize > content.length ? content.length : i+maxChunkSize);
            byte[] chunk = Arrays.copyOfRange(content, from, to);

            if(i == 0) {
                TransactionReceipt receipt = new FileUpdateTransaction()
                        .setFileId(fileId)
                        .setContents(chunk)
                        .setMaxTransactionFee(new Hbar(3))
                        .execute(hederaAccount.getClient())
                        .getReceipt(hederaAccount.getClient());
            }
            else {
                TransactionReceipt receipt = new FileAppendTransaction()
                        .setFileId(fileId)
                        .setContents(chunk)
                        .setMaxTransactionFee(new Hbar(3))
                        .execute(hederaAccount.getClient())
                        .getReceipt(hederaAccount.getClient());
            }
        }
    }

    public static byte[] getFileContent(HederaAccount hederaAccount, FileId fileId) throws Exception {
        ByteString content = new FileContentsQuery()
                .setFileId(fileId)
                .execute(hederaAccount.getClient());

        return content.toByteArray();
    }
}
