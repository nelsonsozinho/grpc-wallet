package com.shire42.customer.repository;

import com.shire42.customer.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@AllArgsConstructor
@Repository
public class CustomerRepository {

    private final DynamoDbEnhancedClient client;

    public void save(Customer customer) {
        final DynamoDbTable<Customer> tableCustomer = getTable();
        tableCustomer.putItem(customer);
    }

    public Customer getById(final String id) {
        final Customer customer = getTable().getItem(
                Key.builder()
                .partitionValue(id)
                .build());
        return customer;
    }

    private DynamoDbTable<Customer> getTable() {
        return client.table("customer", TableSchema.fromBean(Customer.class));
    }

}
