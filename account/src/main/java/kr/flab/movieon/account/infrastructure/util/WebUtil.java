package kr.flab.movieon.account.infrastructure.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;

public final class WebUtil {

    public static final String AUTHORIZATION = "Authorization";
    public static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    public static final String X_REQUESTED_WITH = "X-Requested-With";
    public static final String CONTENT_TYPE = "Content-type";
    public static final String CONTENT_TYPE_JSON = "application/json";

    private WebUtil() {

    }

    public static boolean isAjax(HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    public static boolean isContentTypeJson(HttpServletRequest request) {
        return request.getHeader(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
    }

    public static boolean isPost(HttpServletRequest request) {
        return HttpMethod.POST.name().equals(request.getMethod());
    }
}
