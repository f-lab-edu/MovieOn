package kr.flab.movieon.payment.infrastructure;

public final class TossPaymentsCardInfoResponse {

    private String company;
    private String number;
    private Integer installmentPlanMonths;
    private String approveNo;
    private boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String receiptUrl;
    private String acquireStatus;
    private boolean isInterestFree;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getInstallmentPlanMonths() {
        return installmentPlanMonths;
    }

    public void setInstallmentPlanMonths(Integer installmentPlanMonths) {
        this.installmentPlanMonths = installmentPlanMonths;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public boolean isUseCardPoint() {
        return useCardPoint;
    }

    public void setUseCardPoint(boolean useCardPoint) {
        this.useCardPoint = useCardPoint;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getAcquireStatus() {
        return acquireStatus;
    }

    public void setAcquireStatus(String acquireStatus) {
        this.acquireStatus = acquireStatus;
    }

    public boolean isInterestFree() {
        return isInterestFree;
    }

    public void setInterestFree(boolean interestFree) {
        isInterestFree = interestFree;
    }
}
