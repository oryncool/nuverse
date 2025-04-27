package kz.tech.nuverse.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

public class LanguageUtils {

    private LanguageUtils() {
    }

    public static String getMessage(String valueRu, String valueEn, String valueKz) {

        switch (getLocale().getLanguage()) {
            case "ru":
                return valueRu;
            case "en":
                return valueEn;
            case "kz":
                return valueKz;
            default:
                return valueRu;
        }

    }

    public static Locale getLocale() {
        Locale locale = Locale.getDefault();
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
            if (request.getLocale() != null) {
                locale = request.getLocale();
            }
        }
        return locale;
    }
}
