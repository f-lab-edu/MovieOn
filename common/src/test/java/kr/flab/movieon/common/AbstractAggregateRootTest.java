package kr.flab.movieon.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class AbstractAggregateRootTest {

    @Test
    @DisplayName("이벤트를 등록하고 pollAllEvents()를 호출 후에 남은 이벤트가 없어야 한다.")
    void sut_register_events_and_pollAllEvents() {
        // Arrange
        DomainClass sut = new DomainClass();
        sut.registerEvent(new SampleEvent());
        sut.registerEvent(new SampleEvent());

        // Act
        List<DomainEvent> events = sut.pollAllEvents();

        // Assert
        assertEquals(events.size(), 2);
        assertEquals(sut.pollAllEvents().size(), 0);
    }

    static final class DomainClass extends AbstractAggregateRoot {

    }

    static final class SampleEvent implements DomainEvent {

        @Override
        public Date occurredOn() {
            return null;
        }
    }
}
