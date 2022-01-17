package kr.flab.movieon.notification.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class ExternalEventNotificationProcessDelegator {

    private final List<ExternalEventNotificationProcessor> processors;

    private Map<Class<? extends DomainEvent>, ExternalEventNotificationProcessor> processorMap =
        new HashMap<>();

    public ExternalEventNotificationProcessDelegator(
        List<ExternalEventNotificationProcessor> processors) {
        this.processors = processors;
        setProcessorMap(this.processors);
    }

    private void setProcessorMap(List<ExternalEventNotificationProcessor> processors) {
        for (ExternalEventNotificationProcessor processor : processors) {
            processorMap.put(processor.appliesTo(), processor);
        }
    }

    public Notification process(DomainEvent event) {
        var processor = Objects.requireNonNull(
            processorMap.get(event.getClass()),
            "There is no processor corresponding to the domain event type.");
        return processor.process(event);
    }
}
