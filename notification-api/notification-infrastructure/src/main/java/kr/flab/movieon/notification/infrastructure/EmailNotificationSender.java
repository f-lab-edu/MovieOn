package kr.flab.movieon.notification.infrastructure;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import kr.flab.movieon.notification.domain.EmailNotification;
import kr.flab.movieon.notification.domain.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public final class EmailNotificationSender implements NotificationSender<EmailNotification> {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationSender.class);

    private final JavaMailSender javaMailSender;

    public EmailNotificationSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Class<EmailNotification> appliesTo() {
        return EmailNotification.class;
    }

    @Override
    public void send(EmailNotification notification) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(notification.getEmail());
            helper.setSubject(notification.getTitle());
            helper.setText(notification.getMessage(), true);

            javaMailSender.send(mimeMessage);

            log.info("sent email: {}", notification.getMessage());
        } catch (MessagingException e) {
            log.error("failed to send email", e);
            throw new RuntimeException(e);
        }
    }
}
