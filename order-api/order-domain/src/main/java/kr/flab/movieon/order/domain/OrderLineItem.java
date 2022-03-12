package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kr.flab.movieon.common.error.InvalidArgumentException;

@Entity
@Table(name = "ORDER_LINE_ITEMS")
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long itemId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal basePrice;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<OrderItemOption> options;

    protected OrderLineItem() {

    }

    public OrderLineItem(Long itemId, String name, BigDecimal basePrice,
        List<OrderItemOption> options) {
        if (basePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidArgumentException("주문 상품에 대한 가격이 잘못되었습니다.");
        }
        this.itemId = itemId;
        this.name = name;
        this.basePrice = basePrice;
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public List<OrderItemOption> getOptions() {
        return options;
    }

    public BigDecimal calculatePrice() {
        return this.basePrice.subtract(options.stream()
            .map(OrderItemOption::getSalesPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderLineItem that = (OrderLineItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
