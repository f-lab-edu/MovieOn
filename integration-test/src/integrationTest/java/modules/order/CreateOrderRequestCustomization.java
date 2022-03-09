package modules.order;

import java.util.List;
import kr.flab.movieon.order.application.request.CreateOrderItemOptionRequest;
import kr.flab.movieon.order.application.request.CreateOrderLineItemRequest;
import kr.flab.movieon.order.application.request.CreateOrderRequest;
import org.javaunit.autoparams.customization.Customizer;
import org.javaunit.autoparams.generator.ObjectContainer;
import org.javaunit.autoparams.generator.ObjectGenerationContext;
import org.javaunit.autoparams.generator.ObjectGenerator;

public final class CreateOrderRequestCustomization implements Customizer {

    @Override
    public ObjectGenerator customize(ObjectGenerator generator) {
        return (query, context) -> query.getType().equals(CreateOrderRequest.class)
            ? new ObjectContainer(factory(context))
            : generator.generate(query, context);
    }

    private CreateOrderRequest factory(ObjectGenerationContext context) {
        var options = new CreateOrderItemOptionRequest();
        options.setOptionName("480P 화질");
        options.setSalesPrice(2000);
        var lineItem = new CreateOrderLineItemRequest();
        lineItem.setItemId(1L);
        lineItem.setBasePrice(11000L);
        lineItem.setProductName("(구매) 마이웨이");
        lineItem.setOptions(List.of(options));
        var request = new CreateOrderRequest();
        request.setLineItems(List.of(lineItem));
        request.setPayMethod("CARD");
        request.setUseOfPoint(1200L);
        return request;
    }
}
