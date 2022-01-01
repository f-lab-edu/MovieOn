package kr.flab.movieon.notification.domain;

import java.util.Map;

public interface NotificationTemplateProcessor {

    String process(String template, Map<String, Object> variables);
}
