package com.example.multiplex.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
@Component
public class LogFilter extends OncePerRequestFilter {
    private AtomicLong id = new AtomicLong(1L);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        long requestId = this.id.incrementAndGet();
        doFilterWrapped(
                new RequestWrapper(request, requestId),
                new ResposneWrapper(response),
                filterChain
        );
    }

    protected void doFilterWrapped(RequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) {

        logRequest(request);
    }

    private static void logRequest(RequestWrapper request) {

        StringBuilder builder = new StringBuilder();
         builder.append(String.format(
                "\r\n============= HTTP %s REQUEST MESSAGE START ID[%d] elapsed[] ============\r\n",
                request.getMethod(), request.getId()));
        builder.append("Request URI : " + request.getRequestURI() + "\r\n");
        builder.append("Request URL : " + request.getRequestURL() + "\r\n");


        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerKey = headers.nextElement();
            String headerValue = request.getHeader(headerKey);
            builder.append(String.format("%s : %s\r\n", StringUtils.rightPad(headerKey, 24), headerValue));
        }

        String contentType = request.getContentType();

//        if (contentType.startsWith("application")) {
//            String body = new String(wrequest.toByteArray(), request.getCharacterEncoding() != null ? request.getCharacterEncoding() : "UTF-8");
//            sb.append(System.lineSeparator() + body + System.lineSeparator());
//        }



        log.info(builder.toString());
    }



    private String getAcceptParamValue(String name, String param) {
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(param)) return null;
        try{
            String[] split = StringUtils.split(param, '=');
            if(split != null && split.length > 0) {
                if(StringUtils.startsWithIgnoreCase(StringUtils.trim(split[0]), name)){
                    return split[1];
                }
            }
         return null;
        } catch (Exception e) {
            return null;
        }


    }


}
