package com.example.multiplex.config;

import com.example.multiplex.anno.PageDefault;
import com.example.multiplex.dto.PageRequest;
import com.example.multiplex.type.ISortName;
import com.example.multiplex.type.ISortType;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class PageableArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        Enum sortNameEnum = null, sortTypeEnum = null;
        Integer page = getParamInt(webRequest, "page");
        Integer size = getParamInt(webRequest, "size");
        String sortName = webRequest.getParameter("sortName");
        String sortType = webRequest.getParameter("sortType");

        PageDefault defaults = methodParameter.getParameterAnnotation(PageDefault.class);
        if (defaults != null) {
            page = Optional.ofNullable(page).orElse(defaults.page());
            size = Optional.ofNullable(size).orElse(defaults.size());
            sortName = Optional.ofNullable(sortName).orElse(defaults.sortName());
            sortType = Optional.ofNullable(sortType).orElse(defaults.sortType());

//            필요하면 나중에 추가
//            if (defaults.maxSize() < size)
//                size = defaults.maxSize();
        }
        log.info("methodParameter.getGenericParameterType() : {} ", methodParameter.getGenericParameterType());
        if (methodParameter.getGenericParameterType() instanceof ParameterizedType) {
            ParameterizedType parameterType = (ParameterizedType) methodParameter.getGenericParameterType();
            Type[] types = parameterType.getActualTypeArguments();
            log.info("parameterType.getActualTypeArguments() : {} ", types[0]);
            log.info("parameterType.getActualTypeArguments() : {} ", types[1]);

            if (Strings.isNotBlank(sortName))
                sortNameEnum = enumValueOf(types, sortName, ISortName.class);

            if (Strings.isNotBlank(sortType))
                sortTypeEnum = enumValueOf(types, sortType, ISortType.class);
        }

        return PageRequest.builder().page(page).size(size).sortName(sortNameEnum).sortType(sortTypeEnum).build();
    }

    private Integer getParamInt(NativeWebRequest webRequest, String param) {
        String str = webRequest.getParameter(param);
        if (str != null)
            return Integer.parseInt(str);

        return null;
    }

    private Enum enumValueOf(Type[] types, String sortName, Class cls) {
        try {
            log.info("cls + ");
            return Arrays.stream(types).map(a -> (Class) a).filter(cls::isAssignableFrom).findFirst().map(a -> Enum.valueOf(a, sortName.toUpperCase())).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
