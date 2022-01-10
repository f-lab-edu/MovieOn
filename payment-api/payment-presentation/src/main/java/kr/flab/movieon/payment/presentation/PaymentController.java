package kr.flab.movieon.payment.presentation;

import javax.validation.Valid;
import kr.flab.movieon.payment.application.PaymentCompleteCommand;
import kr.flab.movieon.payment.application.PaymentFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/payments",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public final class PaymentController {

    private final PaymentFacade paymentFacade;

    public PaymentController(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    @PostMapping("/tossSuccess")
    public void completePayment(
        @ModelAttribute @Valid PaymentCompleteCommand request) {
        paymentFacade.completePayment(request);
    }

}
