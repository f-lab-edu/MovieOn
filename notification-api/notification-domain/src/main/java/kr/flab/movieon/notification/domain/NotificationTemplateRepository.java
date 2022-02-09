package kr.flab.movieon.notification.domain;

public interface NotificationTemplateRepository {

    NotificationTemplate save(NotificationTemplate entity);

    NotificationTemplate findByTemplateType(NotificationTemplateType templateType);

    NotificationTemplate findById(Long templateId);
}
