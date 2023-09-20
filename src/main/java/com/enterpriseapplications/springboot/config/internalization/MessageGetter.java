package com.enterpriseapplications.springboot.config.internalization;


import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageGetter
{
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public String getMessage(String code,Object... args) {
        return resourceBundleMessageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }
}
