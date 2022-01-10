package kr.flab.movieon.payment.integrate;

import kr.flab.movieon.payment.application.PaymentFacade;
import kr.flab.movieon.payment.application.PaymentPendingCommand;
import kr.flab.movieon.payment.domain.event.PurchasedPendingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PurchasePaymentEventListener {

    private final PaymentFacade paymentFacade;

    public PurchasePaymentEventListener(
        PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    @EventListener
    public void handle(PurchasedPendingEvent event) {
        var command = new PaymentPendingCommand();
        command.setPurchaseId(event.getPurchaseId());
        command.setPaymentMethod(event.getPaymentMethod());

        paymentFacade.pending(command);
    }
}
