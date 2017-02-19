package com.welcome.studio.welcome.util;

import com.welcome.studio.welcome.model.data.ExceptionJSONInfo;
import com.welcome.studio.welcome.model.RestApiCreator;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Royal on 07.11.2016. !
 */

public class ExceptionUtil {
    public static ExceptionJSONInfo parseEx(Response<?> response){
        Converter<ResponseBody,ExceptionJSONInfo> converter= RestApiCreator.retrofit()
                .responseBodyConverter(ExceptionJSONInfo.class, new Annotation[0]);
        ExceptionJSONInfo exceptionJSONInfo;
        try {
            exceptionJSONInfo=converter.convert(response.errorBody());
        } catch (IOException e) {
            e.printStackTrace();
            return new ExceptionJSONInfo("cannot parse exception response");
        }
        return exceptionJSONInfo;
    }
}
