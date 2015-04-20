package com.github.xumoooo.springcounter;

import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CounterHandlerExceptionResolver extends DefaultHandlerExceptionResolver {

    protected ModelAndView handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                               HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        pageNotFoundLogger.warn(ex.getMessage());
        String[] supportedMethods = ex.getSupportedMethods();
        if (supportedMethods != null) {
            response.setHeader("Allow", StringUtils.arrayToDelimitedString(supportedMethods, ", "));
        }
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView(mappingJackson2JsonView);
        modelAndView.addObject("error", "Method not supported");

        return modelAndView;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
