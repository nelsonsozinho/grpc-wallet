package com.shire42.wallet.grpc;

import br.com.shire42.wallet.ProtoCard;
import br.com.shire42.wallet.ProtoWallet;
import br.com.shire42.wallet.ProtoWalletList;
import br.com.shire42.wallet.WalletRequest;
import br.com.shire42.wallet.WalletServiceGrpc;
import com.shire42.wallet.controller.rest.WalletRest;
import com.shire42.wallet.service.WalletService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;


@AllArgsConstructor
@GrpcService
public class GrpcWalletService extends WalletServiceGrpc.WalletServiceImplBase {

    private final WalletService walletService;

    @Override
    public void findWalletsByCustomer(WalletRequest request, StreamObserver<ProtoWalletList> responseObserver) {
        final List<WalletRest> walletRest = walletService.findWalletsByCustomerId(request.getCustomerId());
        final ProtoWalletList protoWalletList = ProtoWalletList.newBuilder().addAllWallets(walletRest.stream().map(w -> ProtoWallet.newBuilder()
                .setWalletName(w.getWalletName())
                .setCustomerId(w.getCustomerId())
                .setCash(w.getCash())
                .setDescription(w.getDescription())
                .setId(w.getId().toString())
                .addAllCards(toCardList(w))
                .build()).toList())
        .build();

        responseObserver.onNext(protoWalletList);
        responseObserver.onCompleted();

    }

    private List<ProtoCard> toCardList(final WalletRest walletRest) {
        return walletRest.getCards().stream().map(c -> {
            return ProtoCard.newBuilder()
                    .setCvc(c.getCvc())
                    .setId(c.getId().toString())
                    .setExpire(c.getExpire())
                    .setHolderName(c.getHolderName())
                    .setNumber(c.getNumber())
                    .build();
        }).toList();
    }

    @Override
    public void saveNewWallet(ProtoWallet request, StreamObserver<ProtoWallet> responseObserver) {
        super.saveNewWallet(request, responseObserver);
    }

}
