package kr.flab.movieon.payment.infrastructure.jpa;

import kr.flab.movieon.payment.domain.TossPayments;
import kr.flab.movieon.payment.domain.TossPaymentsRepository;

public final class TossPaymentsRepositoryAdapter implements TossPaymentsRepository {

    private final JpaTossPaymentsRepository jpaTossPaymentsRepository;

    public TossPaymentsRepositoryAdapter(JpaTossPaymentsRepository jpaTossPaymentsRepository) {
        this.jpaTossPaymentsRepository = jpaTossPaymentsRepository;
    }

    @Override
    public TossPayments save(TossPayments entity) {
        return jpaTossPaymentsRepository.save(entity);
    }
}
