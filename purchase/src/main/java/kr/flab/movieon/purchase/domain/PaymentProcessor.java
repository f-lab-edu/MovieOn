package kr.flab.movieon.purchase.domain;

public interface PaymentProcessor {

    void payed(Purchase purchase);
}
