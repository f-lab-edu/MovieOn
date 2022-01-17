package kr.flab.movieon.common.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAggregateRoot {

    private List<DomainEvent> events = new ArrayList<>();

    protected void registerEvent(DomainEvent domainEvent) {
        this.events.add(domainEvent);
    }

    public List<DomainEvent> pollAllEvents() {
        if (!events.isEmpty()) {
            var domainEvents = List.copyOf(events);
            this.events.clear();
            return domainEvents;
        } else {
            return Collections.emptyList();
        }
    }
}
