package com.shire42.customer.grpc;

import br.com.shire42.customer.CustomerRequest;
import br.com.shire42.customer.CustomerResponse;
import br.com.shire42.customer.CustomerResponseList;
import br.com.shire42.customer.CustomerServiceGrpc;
import com.shire42.customer.controller.rest.CustomerRest;
import com.shire42.customer.exception.CustomerAlreadyExistsException;
import com.shire42.customer.service.CustomerService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;


@AllArgsConstructor
@GrpcService
public class GrpcCustomerService extends CustomerServiceGrpc.CustomerServiceImplBase {

    private final CustomerService customerService;

    @Override
    public void findCustomer(CustomerRequest request, StreamObserver<CustomerResponseList> responseObserver) {
        final List<CustomerRest> customers = customerService.filterCustomersByEmail(request.getEmail());
        final List<CustomerResponse> grpcResponse = customers.stream().map(c -> {
                    return CustomerResponse.newBuilder()
                            .setName(c.getName())
                            .setLastName(c.getLastName())
                            .setEmail(c.getEmail())
                            .setId(c.getId().toString())
                            .build();
                }).toList();

        final CustomerResponseList responseList = CustomerResponseList.newBuilder().addAllCustomers(grpcResponse).build();

        responseObserver.onNext(responseList);
        responseObserver.onCompleted();
    }

    @Override
    public void saveCustomer(CustomerRequest request, StreamObserver<CustomerResponse> responseObserver) {
        final CustomerRest customer = CustomerRest.builder()
                .name(request.getName())
                .email(request.getEmail())
                .lastName(request.getLastName())
                .build();

        try {
            final CustomerRest response = customerService.createNewCustomer(customer);
            final CustomerResponse grpcResponse =  CustomerResponse.newBuilder()
                    .setId(response.getId().toString())
                    .setName(response.getName())
                    .setLastName(response.getLastName())
                    .setEmail(response.getEmail())
                    .build();
            responseObserver.onNext(grpcResponse);
            responseObserver.onCompleted();
        } catch (CustomerAlreadyExistsException e) {
            responseObserver.onError(new Exception("Error on try to create a new customer"));
        }
    }
}
