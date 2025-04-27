package kz.tech.nuverse.util;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class MessageSourceHelper {

    private static MessageSource injectedMessageSource;

    private MessageSourceHelper() {

    }

    public static String getMessage(String messageKey, Locale locale, Object... arguments) {
        return getInjectedMessageSource().getMessage(messageKey, arguments, messageKey, locale);
    }

    public static MessageSource getInjectedMessageSource() {
        if (injectedMessageSource == null) {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("messages");
            messageSource.setDefaultEncoding("UTF-8");
            injectedMessageSource = messageSource;
        }
        return injectedMessageSource;
    }
}
