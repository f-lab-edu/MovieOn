package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.domain.TossPayments;
import kr.flab.movieon.payment.domain.TossPaymentsPaymentProcessor;
import kr.flab.movieon.payment.domain.TossPaymentsRepository;
import org.springframework.transaction.support.TransactionTemplate;

public final class DefaultTossPaymentsPaymentProcessor implements TossPaymentsPaymentProcessor {

    private final TransactionTemplate transactionTemplate;
    private final TossPaymentsPaymentApprovalCommandVerifier commandVerifier;
    private final TossPaymentsPaymentApprovalProcessor approvalProcessor;
    private final TossPaymentsRepository tossPaymentsRepository;

    public DefaultTossPaymentsPaymentProcessor(
        TransactionTemplate transactionTemplate,
        TossPaymentsPaymentApprovalCommandVerifier commandVerifier,
        TossPaymentsPaymentApprovalProcessor approvalProcessor,
        TossPaymentsRepository tossPaymentsRepository) {
        this.transactionTemplate = transactionTemplate;
        this.commandVerifier = commandVerifier;
        this.approvalProcessor = approvalProcessor;
        this.tossPaymentsRepository = tossPaymentsRepository;
    }

    @Override
    public TossPayments payed(String orderId, String paymentKey, Integer amount) {
        return transactionTemplate.execute(status -> {
            commandVerifier.verify(orderId, paymentKey, amount);
            var response = approvalProcessor
                .approval(orderId, paymentKey, amount);
            var tossPayments = TossPaymentsFactory.create(response);
            tossPaymentsRepository.save(tossPayments);
            return tossPayments;
        });
    }
}
