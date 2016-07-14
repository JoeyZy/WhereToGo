package com.luxoft.wheretogo.configuration;

/**
 * Created by sasha on 06.07.16.
 */
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.core.annotation.Order;

@Order(2)
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    //do nothing
}
