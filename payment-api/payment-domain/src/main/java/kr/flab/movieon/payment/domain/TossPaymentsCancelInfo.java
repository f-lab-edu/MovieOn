package kr.flab.movieon.payment.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class TossPaymentsCancelInfo {

    protected TossPaymentsCancelInfo() {

    }

    private Integer cancelAmount;
    private Integer refundableAmount;
    private String cancelReason;
    private Integer taxFreeAmountCancel;
    private LocalDateTime canceledAt;

    public TossPaymentsCancelInfo(Integer cancelAmount, Integer refundableAmount,
        String cancelReason, Integer taxFreeAmountCancel, LocalDateTime canceledAt) {
        this.cancelAmount = cancelAmount;
        this.refundableAmount = refundableAmount;
        this.cancelReason = cancelReason;
        this.taxFreeAmountCancel = taxFreeAmountCancel;
        this.canceledAt = canceledAt;
    }

    public Integer getCancelAmount() {
        return cancelAmount;
    }

    public Integer getRefundableAmount() {
        return refundableAmount;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public Integer getTaxFreeAmountCancel() {
        return taxFreeAmountCancel;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TossPaymentsCancelInfo that = (TossPaymentsCancelInfo) o;
        return Objects.equals(cancelAmount, that.cancelAmount) && Objects.equals(
            refundableAmount, that.refundableAmount) && Objects.equals(cancelReason,
            that.cancelReason) && Objects.equals(taxFreeAmountCancel, that.taxFreeAmountCancel)
            && Objects.equals(canceledAt, that.canceledAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cancelAmount, refundableAmount, cancelReason, taxFreeAmountCancel,
            canceledAt);
    }
}
