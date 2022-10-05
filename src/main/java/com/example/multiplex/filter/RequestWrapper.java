package com.example.multiplex.filter;

import lombok.Getter;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;

@Getter
public class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] cachedInputStream;
    private long id;

    public RequestWrapper(HttpServletRequest request, long id) throws IOException {
        super(request);
        this.id = id;
        InputStream inputStream = request.getInputStream();
        this.cachedInputStream = StreamUtils.copyToByteArray(inputStream);
    }
}
