package kr.flab.movieon.account.infrastructure.jwt.fixtures;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfo;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfoRepository;

public final class FakeRefreshTokenInfoRepository implements RefreshTokenInfoRepository {

    private final Map<Long, RefreshTokenInfo> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public RefreshTokenInfo save(RefreshTokenInfo entity) {
        return data.put(idGenerator.incrementAndGet(), entity);
    }

    @Override
    public boolean existsByRefreshTokenJti(String jti) {
        return data.values().stream()
            .anyMatch(token -> token.getRefreshTokenJti().equals(jti));
    }

    @Override
    public RefreshTokenInfo findByRefreshTokenJti(String jti) {
        return data.values().stream()
            .filter(token -> token.getRefreshTokenJti().equals(jti))
            .findFirst()
            .get();
    }
}
