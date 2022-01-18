package kr.flab.movieon.payment.infrastructure;

import java.math.BigDecimal;
import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.Payment.Type;
import kr.flab.movieon.payment.domain.PaymentApiProvider;
import kr.flab.movieon.payment.domain.PaymentApprovalCommand;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentCommandException;
import lombok.Data;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public final class TossPaymentProvider implements PaymentApiProvider {

    private final WebClient webClient;

    public TossPaymentProvider(WebClient webClient) {
        this.webClient = webClient.mutate().baseUrl("https://pay.toss.im/api/v2").build();
    }

    @Override
    public boolean support(Payment.Type type) {
        return Type.TOSS == type;
    }

    @Override
    public Payment pay(PaymentApprovalCommand command) {
        //TODO Toss API Key 설정
        String apiKey = "토스 API KEY";

        var infoResponse = getPaymentInfo(apiKey, command.getPgToken());
        if (validate(command, infoResponse)) {
            throw new InvalidPaymentCommandException("Invalid payment approval request.");
        }

        var approvalResponse = approvePayment(apiKey, command.getPgToken());
        if (approvalResponse.getCode() == -1) {
            throw new InvalidPaymentCommandException(
                "Toss " + approvalResponse.getErrorCode() + " " + approvalResponse.getMsg());
        }

        var payment = Payment.create(command.getOrderId(), command.getOrderName(),
            command.getPurchaserId(), command.getAmount(), command.getPaymentType());

        payment.approve();
        return payment;
    }

    private PaymentApprovalResponse approvePayment(String apiKey, String payToken) {
        var request = new PaymentApprovalRequest();
        request.setApiKey(apiKey);
        request.setPayToken(payToken);

        var response = this.webClient
            .post()
            .uri("/execute")
            .body(Mono.just(request), PaymentApprovalRequest.class)
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                clientResponse -> clientResponse
                    .bodyToMono(String.class)
                    .map(InvalidPaymentCommandException::new))
            .bodyToMono(PaymentApprovalResponse.class);

        return response.flux().toStream().findFirst()
            .orElseThrow(InvalidPaymentCommandException::new);
    }

    private boolean validate(PaymentApprovalCommand command, PaymentInfoResponse response) {
        return command.getAmount().equals(BigDecimal.valueOf(response.getAmount()))
            && command.getOrderId().equals(Long.valueOf(response.getOrderNo()))
            && response.getPayStatus().equals("PAY_APPROVED");
    }

    private PaymentInfoResponse getPaymentInfo(String apiKey, String payToken) {
        var request = new PaymentInfoRequest();
        request.setApiKey(apiKey);
        request.setPayToken(payToken);

        var response = this.webClient
            .post()
            .uri("/status")
            .body(Mono.just(request), PaymentInfoRequest.class).retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                clientResponse -> clientResponse
                    .bodyToMono(String.class)
                    .map(InvalidPaymentCommandException::new))
            .bodyToMono(PaymentInfoResponse.class);

        return response.flux().toStream().findFirst()
            .orElseThrow(InvalidPaymentCommandException::new);
    }

    @Data
    private static class PaymentInfoRequest {

        private String apiKey;
        private String payToken;
    }

    @Data
    private static class PaymentInfoResponse {

        private String payStatus;
        private String payToken;
        private String orderNo;
        private Integer amount;
    }

    @Data
    private static class PaymentApprovalRequest {

        private String apiKey;
        private String payToken;
    }

    @Data
    private static class PaymentApprovalResponse {

        private Integer code;
        private String msg;
        private String errorCode;
    }

}
