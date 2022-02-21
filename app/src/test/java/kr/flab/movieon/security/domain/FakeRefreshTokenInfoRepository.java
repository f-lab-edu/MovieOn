package kr.flab.movieon.security.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import kr.flab.movieon.security.integrate.domain.RefreshTokenInfo;
import kr.flab.movieon.security.integrate.domain.RefreshTokenInfoRepository;

final class FakeRefreshTokenInfoRepository implements RefreshTokenInfoRepository {

    private final Map<Long, RefreshTokenInfo> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public boolean existsByRefreshTokenJti(String refreshTokenJti) {
        return data.values().stream()
            .anyMatch(entity -> entity.getRefreshTokenJti().equals(refreshTokenJti));
    }

    @Override
    public Optional<RefreshTokenInfo> findByRefreshTokenJti(String refreshTokenJti) {
        return data.values().stream()
            .filter(entity -> entity.getRefreshTokenJti().equals(refreshTokenJti)).findAny();
    }

    @Override
    public RefreshTokenInfo save(RefreshTokenInfo entity) {
        return data.put(idGenerator.incrementAndGet(), entity);
    }

    @Override
    public void delete(RefreshTokenInfo entity) {
        data.remove(entity.getId());
    }
}
