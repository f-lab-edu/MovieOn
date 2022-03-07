package kr.flab.movieon.point.integrate;

import java.math.BigDecimal;
import kr.flab.movieon.order.domain.PointManager;
import org.springframework.stereotype.Component;

@Component
public final class DefaultPointManager implements PointManager {

    @Override
    public void canUseOfPoint(String customerId, BigDecimal useOfPoint) {

    }
}
