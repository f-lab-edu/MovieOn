package kr.flab.movieon.notification.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class FakeNotificationSettingRepository implements NotificationSettingRepository {

    private final Map<Long, NotificationSetting> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public NotificationSetting save(NotificationSetting entity) {
        entity.setId(idGenerator.incrementAndGet());
        data.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public NotificationSetting findByReceiver(Receiver receiver) {
        return data.values()
            .stream()
            .filter(s -> s.getReceiver().equals(receiver))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
