package kr.flab.movieon.order.integrate;

import java.util.List;
import kr.flab.movieon.order.domain.Item;
import kr.flab.movieon.order.domain.ItemRepository;
import org.springframework.stereotype.Component;

@Component
public final class ItemRepositoryAdapterInOrderModule implements ItemRepository {

    @Override
    public List<Item> findAllById(List<Long> productIds) {
        return null;
    }
}
