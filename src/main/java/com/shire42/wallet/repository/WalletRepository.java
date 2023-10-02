package com.shire42.wallet.repository;

import com.shire42.wallet.model.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@AllArgsConstructor
@Repository
public class WalletRepository {

    private final DynamoDbEnhancedClient client;

    public void save(Wallet customer) {
        final DynamoDbTable<Wallet> tableCustomer = getTable();
        tableCustomer.putItem(customer);
    }

    public Wallet getById(final String id) {
        return getTable().getItem(
                Key.builder()
                .partitionValue(id)
                .build());
    }

    private DynamoDbTable<Wallet> getTable() {
        return client.table("wallet", TableSchema.fromBean(Wallet.class));
    }

}
