package kr.flab.movieon.payment.infrastructure;

import java.util.HashMap;
import java.util.Map;
import kr.flab.movieon.payment.domain.TossPaymentsPaymentApprovalCommand;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public final class TossPaymentsPaymentApprovalProcessor {

    private final RestTemplate restTemplate;
    private final TossPaymentsProperties tossPaymentsProperties;

    public TossPaymentsPaymentApprovalProcessor(RestTemplate restTemplate,
        TossPaymentsProperties tossPaymentsProperties) {
        this.restTemplate = restTemplate;
        this.tossPaymentsProperties = tossPaymentsProperties;
    }

    // TODO 결제 승인 요청 과정에서 실패한 경우 예외처리 필요.
    public TossPaymentsPaymentCompletedResponse approval(
        TossPaymentsPaymentApprovalCommand command) {
        var request = createRequest(createHeaders(), command);
        var result = restTemplate.postForEntity(
            tossPaymentsProperties.getUrl() + command.getPaymentKey(), request,
            TossPaymentsPaymentCompletedResponse.class);
        return result.getBody();
    }

    private HttpEntity<Map<String, String>> createRequest(HttpHeaders headers,
        TossPaymentsPaymentApprovalCommand command) {
        var payloadMap = new HashMap<String, String>();
        payloadMap.put("orderId", command.getOrderId());
        payloadMap.put("amount", String.valueOf(command.getAmount()));
        return new HttpEntity<>(payloadMap, headers);
    }

    private HttpHeaders createHeaders() {
        var headers = new HttpHeaders();
        headers.setBasicAuth(tossPaymentsProperties.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}