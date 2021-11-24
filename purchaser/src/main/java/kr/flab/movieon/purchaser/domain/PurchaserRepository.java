package kr.flab.movieon.purchaser.domain;

public interface PurchaserRepository {

    Purchaser save(Purchaser entity);

    Purchaser findById(Long purchaserId);
}
