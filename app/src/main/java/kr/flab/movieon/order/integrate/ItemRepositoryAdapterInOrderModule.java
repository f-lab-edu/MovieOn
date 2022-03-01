package kr.flab.movieon.order.integrate;

import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.order.domain.Item;
import kr.flab.movieon.order.domain.ItemOption;
import kr.flab.movieon.order.domain.ItemRepository;
import org.springframework.stereotype.Component;

@Component
public final class ItemRepositoryAdapterInOrderModule implements ItemRepository {

    private final kr.flab.movieon.product.domain.ItemRepository itemRepository;

    public ItemRepositoryAdapterInOrderModule(
        kr.flab.movieon.product.domain.ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAllById(List<Long> itemIds) {
        var item = itemRepository.findAllById(itemIds);
        return item.stream()
            .map(i -> new Item(i.getId(), i.getName(), i.getBasePrice(),
                i.getOptions()
                    .stream()
                    .map(o -> new ItemOption(o.getOptionName(), o.getSalesPrice()))
                    .collect(Collectors.toList()
                    ))
            )
            .collect(Collectors.toList());
    }
}
