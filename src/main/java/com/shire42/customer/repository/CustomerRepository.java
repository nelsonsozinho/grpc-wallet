package com.shire42.customer.repository;

import com.shire42.customer.model.Customer;
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
public class CustomerRepository {

    private final DynamoDbEnhancedClient client;

    public void save(Customer customer) {
        final DynamoDbTable<Customer> tableCustomer = getTable();
        tableCustomer.putItem(customer);
    }

    public Customer getById(final String id) {
        return getTable().getItem(
                Key.builder()
                .partitionValue(id)
                .build());
    }

    public List<Customer> filterByEmail(final String email) {
        final Map<String, AttributeValue> params = Map.of(":email", stringValue(email));

        final Expression expression = Expression.builder()
                .expression("email = :email")
                .expressionValues(params)
                .build();

        final ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .consistentRead(true)
                .attributesToProject("id", "name", "last-name", "email")
                .filterExpression(expression)
                .build();

        return getTable().scan(request).items().stream().toList();
    }

    private DynamoDbTable<Customer> getTable() {
        return client.table("customer", TableSchema.fromBean(Customer.class));
    }

}
