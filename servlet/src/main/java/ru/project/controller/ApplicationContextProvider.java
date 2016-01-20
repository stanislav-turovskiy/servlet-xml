package ru.project.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        AppContext.setApplicationContext(context);
    }
}
