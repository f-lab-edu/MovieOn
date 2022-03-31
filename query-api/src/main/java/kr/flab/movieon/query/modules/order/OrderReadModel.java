package kr.flab.movieon.query.modules.order;

import java.time.LocalDateTime;
import java.util.List;

public final class OrderReadModel {

    private String orderId;
    private String customerId;
    private String status;
    private Integer totalAmount;
    private Integer useOfPoint;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime completedAt;
    private List<OrderLineItemReadModel> lineItems;

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public Integer getUseOfPoint() {
        return useOfPoint;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public List<OrderLineItemReadModel> getLineItems() {
        return lineItems;
    }
}
