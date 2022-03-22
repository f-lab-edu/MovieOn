package kr.flab.movieon.payment.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.flab.movieon.common.domain.model.AbstractAggregateRoot;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@DynamicInsert
@DynamicUpdate
public class TossPayments extends AbstractAggregateRoot {

    protected TossPayments() {

    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private TossPaymentsInfo info;
    @Embedded
    private TossPaymentsCancelInfo cancels;
    @Embedded
    private TossPaymentsCardInfo card;
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    private TossPayments(TossPaymentsInfo info,
        TossPaymentsCancelInfo cancels, TossPaymentsCardInfo card,
        PayMethod payMethod) {
        this.info = info;
        this.cancels = cancels;
        this.card = card;
        this.payMethod = payMethod;
        registerEvent(new PaymentCompletedEvent(this));
    }

    public static TossPayments create(TossPaymentsInfo info,
        TossPaymentsCancelInfo cancels, TossPaymentsCardInfo card,
        PayMethod payMethod) {
        return new TossPayments(info, cancels, card, payMethod);
    }

    public Long getId() {
        return id;
    }

    public TossPaymentsInfo getInfo() {
        return info;
    }

    public TossPaymentsCancelInfo getCancels() {
        return cancels;
    }

    public TossPaymentsCardInfo getCard() {
        return card;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TossPayments that = (TossPayments) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
