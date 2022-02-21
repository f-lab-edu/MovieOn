package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.domain.PayMethod;
import kr.flab.movieon.payment.domain.TossPayments;
import kr.flab.movieon.payment.domain.TossPaymentsCardInfo;
import kr.flab.movieon.payment.domain.TossPaymentsCardInfo.TossPaymentsAcquireStatus;
import kr.flab.movieon.payment.domain.TossPaymentsCardInfoBuilder;
import kr.flab.movieon.payment.domain.TossPaymentsInfo;
import kr.flab.movieon.payment.domain.TossPaymentsInfoBuilder;

public final class TossPaymentsFactory {

    private TossPaymentsFactory() {

    }

    public static TossPayments create(TossPaymentsPaymentCompletedResponse body) {
        return new TossPayments(createInfo(body), null,
            createCardInfo(body.getCard()), PayMethod.valueOf(body.getMethod()));
    }

    private static TossPaymentsCardInfo createCardInfo(TossPaymentsCardInfoResponse response) {
        return TossPaymentsCardInfoBuilder.builder()
            .approveNo(response.getApproveNo())
            .acquireStatus(TossPaymentsAcquireStatus.valueOf(response.getAcquireStatus()))
            .cardType(response.getCardType())
            .company(response.getCompany())
            .installmentPlanMonths(response.getInstallmentPlanMonths())
            .isInterestFree(response.isInterestFree())
            .number(response.getNumber())
            .ownerType(response.getOwnerType())
            .receiptUrl(response.getReceiptUrl())
            .useCardPoint(response.isUseCardPoint())
            .build();
    }

    private static TossPaymentsInfo createInfo(TossPaymentsPaymentCompletedResponse body) {
        return TossPaymentsInfoBuilder.builder()
            .mid(body.getMid())
            .orderId(body.getOrderId())
            .orderName(body.getOrderName())
            .paymentKey(body.getPaymentKey())
            .transactionKey(body.getTransactionKey())
            .vat(body.getVat())
            .version(body.getVersion())
            .type(body.getType())
            .totalAmount(body.getTotalAmount())
            .balanceAmount(body.getBalanceAmount())
            .suppliedAmount(body.getBalanceAmount())
            .taxFreeAmount(body.getTaxFreeAmount())
            .status(body.getStatus())
            .approvedAt(body.getApprovedAt())
            .requestedAt(body.getRequestedAt())
            .build();
    }
}
