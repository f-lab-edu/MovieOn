package kr.flab.movieon.payment.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class TossPaymentsCardInfo {

    protected TossPaymentsCardInfo() {

    }

    private String company;
    private String number;
    private Integer installmentPlanMonths;
    private String approveNo;
    private boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String receiptUrl;
    @Enumerated(EnumType.STRING)
    private TossPaymentsAcquireStatus acquireStatus;
    private boolean isInterestFree;

    public TossPaymentsCardInfo(String company, String number, Integer installmentPlanMonths,
        String approveNo, boolean useCardPoint, String cardType, String ownerType,
        String receiptUrl,
        TossPaymentsAcquireStatus acquireStatus, boolean isInterestFree) {
        this.company = company;
        this.number = number;
        this.installmentPlanMonths = installmentPlanMonths;
        this.approveNo = approveNo;
        this.useCardPoint = useCardPoint;
        this.cardType = cardType;
        this.ownerType = ownerType;
        this.receiptUrl = receiptUrl;
        this.acquireStatus = acquireStatus;
        this.isInterestFree = isInterestFree;
    }

    public enum TossPaymentsAcquireStatus {
        READY("매입 대기"),
        REQUESTED("매입 요청됨"),
        COMPLETED("매입 완료"),
        CANCEL_REQUESTED("매입 취소 요청됨"),
        CANCELED("매입 취소 완료");

        private final String description;

        TossPaymentsAcquireStatus(String description) {
            this.description = description;
        }
    }

    public String getCompany() {
        return company;
    }

    public String getNumber() {
        return number;
    }

    public Integer getInstallmentPlanMonths() {
        return installmentPlanMonths;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public boolean isUseCardPoint() {
        return useCardPoint;
    }

    public String getCardType() {
        return cardType;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public TossPaymentsAcquireStatus getAcquireStatus() {
        return acquireStatus;
    }

    public boolean isInterestFree() {
        return isInterestFree;
    }
}
