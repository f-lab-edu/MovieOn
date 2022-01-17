package kr.flab.movieon.order.domain;

public final class DummyOrderValidator implements OrderValidator {

    @Override
    public boolean validate(Order order) {
        return true;
    }
}
