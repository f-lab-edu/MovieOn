package kr.flab.movieon.order.domain;

import java.math.BigDecimal;

public interface PointManager {

    void canUseOfPoint(Long customerId, BigDecimal useOfPoint);
}
