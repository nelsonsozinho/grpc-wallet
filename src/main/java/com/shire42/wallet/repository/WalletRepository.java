package com.shire42.wallet.repository;

import com.shire42.wallet.model.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

import static software.amazon.awssdk.enhanced.dynamodb.internal.AttributeValues.stringValue;

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

    public List<Wallet> findByCustomerId(String customerId) {
        final Map<String, AttributeValue> params = Map.of(":customerId", stringValue(customerId));

        final Expression expression = Expression.builder()
                .expression("customerId = :customerId")
                .expressionValues(params)
                .build();

        final ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .consistentRead(true)
                .attributesToProject("id", "customerId", "walletName", "description", "cash", "cards")
                .filterExpression(expression)
                .build();

        return getTable().scan(request).items().stream().toList();
    }

    private DynamoDbTable<Wallet> getTable() {
        return client.table("wallet", TableSchema.fromBean(Wallet.class));
    }

}
