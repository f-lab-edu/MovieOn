package kr.flab.movieon.order.application;

import java.math.BigDecimal;
import kr.flab.movieon.order.domain.PointManager;

final class DummyPointManager implements PointManager {

    @Override
    public void canUseOfPoint(String customerId, BigDecimal useOfPoint) {

    }
}
