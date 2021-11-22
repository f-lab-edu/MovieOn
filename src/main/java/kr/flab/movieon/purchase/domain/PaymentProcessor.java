package kr.flab.movieon.purchase.domain;

public interface PaymentProcessor {

    Purchase payed(Purchase purchase);
}
