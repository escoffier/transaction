package com.example.transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Order(1)
@WebFilter(filterName = "LogRequestFilter", urlPatterns = "/*")
public class LogRequestFilter extends OncePerRequestFilter {

    private Log log = LogFactory.getLog(getClass());

    private ErrorAttributes errorAttributes;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("--------LogRequestFilter doFilterInternal");
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        int status = HttpStatus.BAD_REQUEST.value();
        filterChain.doFilter(httpServletRequest, httpServletResponse);

        status = httpServletResponse.getStatus();

        if (status > HttpStatus.BAD_REQUEST.value()){
            Map<String, Object> trace = getTrace(httpServletRequest, status);
            getBody(requestWrapper, trace);
            logTrace(httpServletRequest, trace);

        }
    }

    private void getBody(ContentCachingRequestWrapper request, Map<String, Object> trace) {
        // wrap request to make sure we can read the body of the request (otherwise it will be consumed by the actual
        // request handler)
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if(wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();

            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException e){
                    payload = "[unknown]";

                }
                trace.put("body", payload);
            }
        }
    }

    private void logTrace(HttpServletRequest request, Map<String, Object> trace) {
        Object method = trace.get("method");
        Object path = trace.get("path");
        Object statusCode = trace.get("statusCode");

        logger.info(String.format("%s %s produced an error status code '%s'. Trace: '%s'", method, path, statusCode,
                trace));
    }

    private Map<String, Object> getTrace(HttpServletRequest request, int status) {
        Throwable exception = (Throwable)request.getAttribute("javax.servlet.error.exception");

        //Principal principal = request.getUserPrincipal();

        Map<String, Object> trace = new LinkedHashMap<>();

        trace.put("method", request.getMethod());
        trace.put("path", request.getRequestURI());
        //trace.put("principal", principal.getName());
        trace.put("query", request.getQueryString());
        trace.put("statusCode", status);

//        if (exception != null && this.errorAttributes != null) {
//            trace.put("error", this.errorAttributes
//                    .getErrorAttributes(new ServletRequestAttributes(request), true));
//        }

        return trace;

    }
}
