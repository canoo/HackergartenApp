package com.canoo.hackergarten.register.web.server;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.restlet.data.Form;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author edewit
 */
public class FormPostHandler {
    private static final FormPostHandler INSTANCE = new FormPostHandler();

    private FormPostHandler() {
        ConvertUtils.register(new Converter() {
            public Object convert(Class inClass, Object o) {
                return new Date(Long.valueOf(o.toString()));
            }
        }, Date.class);
    }

    public static FormPostHandler getInstance() {
        return INSTANCE;
    }

    public void copyFormParameters(Object inEntity, Form inForm) {
        for (Field field : inEntity.getClass().getDeclaredFields()) {
            String value = inForm.getFirstValue(field.getName());
            if (value != null) {
                try {
                    BeanUtils.setProperty(inEntity, field.getName(), value);
                } catch (Exception e) {
                    throw new RuntimeException("could not set bean value", e);
                }
            }
        }
    }
}
