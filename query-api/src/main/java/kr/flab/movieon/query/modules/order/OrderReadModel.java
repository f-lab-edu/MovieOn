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

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setUseOfPoint(Integer useOfPoint) {
        this.useOfPoint = useOfPoint;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setLineItems(
        List<OrderLineItemReadModel> lineItems) {
        this.lineItems = lineItems;
    }
}
