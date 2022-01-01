package kr.flab.movieon.notification.infrastructure;

import java.util.Map;
import kr.flab.movieon.notification.domain.NotificationTemplateProcessor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public final class ThymeleafTemplateProcessor implements NotificationTemplateProcessor {

    private final TemplateEngine templateEngine;

    public ThymeleafTemplateProcessor(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String process(String template, Map<String, Object> variables) {
        var context = new Context();
        context.setVariables(variables);
        return templateEngine.process(template, context);
    }
}
