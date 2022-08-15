package kr.flab.movieon.notification.domain;

public interface NotificationTemplateRepository {

    NotificationTemplate save(NotificationTemplate entity);

    NotificationTemplate findByTemplate(String typeName, String templateName);
}
