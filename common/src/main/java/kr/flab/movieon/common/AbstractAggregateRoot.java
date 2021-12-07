package kr.flab.movieon.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAggregateRoot {

    private List<DomainEvent> events = new ArrayList<>();

    protected void registerEvent(DomainEvent domainEvent) {
        this.events.add(domainEvent);
    }

    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }
}
