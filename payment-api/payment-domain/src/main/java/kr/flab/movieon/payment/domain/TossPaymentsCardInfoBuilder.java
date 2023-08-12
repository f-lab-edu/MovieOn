package kr.flab.movieon.payment.domain;

import kr.flab.movieon.payment.domain.TossPaymentsCardInfo.TossPaymentsAcquireStatus;

public final class TossPaymentsCardInfoBuilder {

    private String company;
    private String number;
    private Integer installmentPlanMonths;
    private String approveNo;
    private boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String receiptUrl;
    private TossPaymentsAcquireStatus acquireStatus;
    private boolean isInterestFree;

    private TossPaymentsCardInfoBuilder() {

    }

    public static TossPaymentsCardInfoBuilder builder() {
        return new TossPaymentsCardInfoBuilder();
    }

    public TossPaymentsCardInfoBuilder company(String company) {
        this.company = company;
        return this;
    }

    public TossPaymentsCardInfoBuilder number(String number) {
        this.number = number;
        return this;
    }

    public TossPaymentsCardInfoBuilder installmentPlanMonths(Integer installmentPlanMonths) {
        this.installmentPlanMonths = installmentPlanMonths;
        return this;
    }

    public TossPaymentsCardInfoBuilder approveNo(String approveNo) {
        this.approveNo = approveNo;
        return this;
    }

    public TossPaymentsCardInfoBuilder cardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public TossPaymentsCardInfoBuilder useCardPoint(boolean useCardPoint) {
        this.useCardPoint = useCardPoint;
        return this;
    }

    public TossPaymentsCardInfoBuilder ownerType(String ownerType) {
        this.ownerType = ownerType;
        return this;
    }

    public TossPaymentsCardInfoBuilder receiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
        return this;
    }

    public TossPaymentsCardInfoBuilder acquireStatus(TossPaymentsAcquireStatus acquireStatus) {
        this.acquireStatus = acquireStatus;
        return this;
    }

    public TossPaymentsCardInfoBuilder isInterestFree(boolean isInterestFree) {
        this.isInterestFree = isInterestFree;
        return this;
    }

    public TossPaymentsCardInfo build() {
        return new TossPaymentsCardInfo(company, number, installmentPlanMonths,
                approveNo, useCardPoint, cardType, ownerType,
                receiptUrl, acquireStatus, isInterestFree);
    }
}
