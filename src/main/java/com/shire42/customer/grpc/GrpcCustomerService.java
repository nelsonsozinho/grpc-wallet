package com.shire42.customer.grpc;

import br.com.shire42.customer.CustomerRequest;
import br.com.shire42.customer.CustomerResponse;
import br.com.shire42.customer.CustomerResponseList;
import br.com.shire42.customer.CustomerServiceGrpc;
import com.shire42.customer.service.CustomerService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;


@AllArgsConstructor
@GrpcService
public class GrpcCustomerService extends CustomerServiceGrpc.CustomerServiceImplBase {

    private final CustomerService customerService;

    @Override
    public void findCustomer(CustomerRequest request, StreamObserver<CustomerResponseList> responseObserver) {
        super.findCustomer(request, responseObserver);

    }

    @Override
    public void saveCustomer(CustomerRequest request, StreamObserver<CustomerResponse> responseObserver) {
        super.saveCustomer(request, responseObserver);
    }
}
