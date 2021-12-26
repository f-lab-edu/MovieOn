package kr.flab.movieon.notification.infrastructure;

import kr.flab.movieon.notification.domain.RegisteredAccountConfirmEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class EventHandler {

    @EventListener
    public void handle(RegisteredAccountConfirmEvent event) {
        // 이메일 형식의 알림 메세지 생성

        // 알림 메세지 영속화

        // 템플릿 모델을 저장소 개체로부터 조회

        // 템플릿 엔진을 사용하여 조회한 템플릿 모델을 HTML 파일로 변환

        // 메세지 발송(비동기)
    }
}
