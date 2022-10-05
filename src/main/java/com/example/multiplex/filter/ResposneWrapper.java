package com.example.multiplex.filter;

import jdk.javadoc.internal.doclets.toolkit.Content;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;

public class ResposneWrapper extends ContentCachingResponseWrapper {
    public ResposneWrapper(HttpServletResponse response) {
        super(response);
    }
}
