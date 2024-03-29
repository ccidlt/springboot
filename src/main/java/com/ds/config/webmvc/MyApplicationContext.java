package com.ds.config.webmvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationContext implements ApplicationContextAware, EnvironmentAware {

    public static ApplicationContext applicationContext = null;
    public static Environment environment = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MyApplicationContext.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> bean) {
        return applicationContext.getBean(bean);
    }

    @EventListener
    public void myEventListen(MyEvent myEvent){
        System.out.println(myEvent.getSource().toString());
    }

    @Override
    public void setEnvironment(Environment environment) {
        MyApplicationContext.environment = environment;
    }

    public static Environment getEnvironment() {
        return environment;
    }
}
