package kr.flab.movieon.purchase.domain;

public interface PurchaseRepository {

    Purchase save(Purchase entity);

    Purchase findById(Long purchaseId);
}
