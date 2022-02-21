package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.domain.TossPayments;
import kr.flab.movieon.payment.domain.TossPaymentsPaymentApprovalCommand;
import kr.flab.movieon.payment.domain.TossPaymentsPaymentProcessor;
import kr.flab.movieon.payment.domain.TossPaymentsRepository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
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
    public TossPayments payed(TossPaymentsPaymentApprovalCommand command) {
        commandVerifier.verify(command);
        var response = approvalProcessor.approval(command);
        var tossPayments = TossPaymentsFactory.create(response);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(
                TransactionStatus status) {
                tossPaymentsRepository.save(tossPayments);
            }
        });
        return tossPayments;
    }
}
