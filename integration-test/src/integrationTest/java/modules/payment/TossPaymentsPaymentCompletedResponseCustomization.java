package modules.payment;

import static java.time.LocalDateTime.now;

import kr.flab.movieon.payment.infrastructure.TossPaymentsCardInfoResponse;
import kr.flab.movieon.payment.infrastructure.TossPaymentsPaymentCompletedResponse;
import org.javaunit.autoparams.customization.Customizer;
import org.javaunit.autoparams.generator.ObjectContainer;
import org.javaunit.autoparams.generator.ObjectGenerationContext;
import org.javaunit.autoparams.generator.ObjectGenerator;

public final class TossPaymentsPaymentCompletedResponseCustomization implements Customizer {

    @Override
    public ObjectGenerator customize(ObjectGenerator generator) {
        return (query, context) ->
            query.getType().equals(TossPaymentsPaymentCompletedResponse.class)
                ? new ObjectContainer(factory(context))
                : generator.generate(query, context);
    }

    private TossPaymentsPaymentCompletedResponse factory(ObjectGenerationContext context) {
        var response = factory(factory());
        return response;
    }

    private TossPaymentsCardInfoResponse factory() {
        var card = new TossPaymentsCardInfoResponse();
        card.setCompany("무비온");
        card.setCardType("신용");
        card.setAcquireStatus("READY");
        card.setOwnerType("개인");
        card.setUseCardPoint(false);
        card.setApproveNo("00000000");
        card.setNumber("433012******1234");
        card.setInstallmentPlanMonths(0);
        card.setInterestFree(false);
        card.setReceiptUrl("https://merchants.tosspayments.com/web/serve/"
            + "merchant/test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq/receipt/fake-key");
        return card;
    }

    private TossPaymentsPaymentCompletedResponse factory(TossPaymentsCardInfoResponse card) {
        var response = new TossPaymentsPaymentCompletedResponse();
        response.setApprovedAt(now());
        response.setVersion("1.3");
        response.setPaymentKey("fake-key");
        response.setMid("tosspayments");
        response.setOrderId("ord_202203101620471034855694");
        response.setOrderName("(구매) 마이웨이");
        response.setType("NORMAL");
        response.setMethod("카드");
        response.setCurrency("KRW");
        response.setUseEscrow(false);
        response.setCultureExpense(false);
        response.setTotalAmount(7800);
        response.setBalanceAmount(7800);
        response.setSuppliedAmount(7120);
        response.setStatus("DONE");
        response.setSecret(null);
        response.setRequestedAt(now());
        response.setVat(0);
        response.setTaxFreeAmount(0);
        response.setTransactionKey(null);
        response.setCard(card);
        return response;
    }
}
