package org.leach.scrumcards.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leach.scrumcards.dto.Result;
import org.leach.scrumcards.dto.ResultCode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Leach
 * @date 2017/9/25
 */
public abstract class BaseController {

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public <T> Result<T> response(int code, String message, T model) {
        Result<T> result = new Result();
        result.setCode(code);
        result.setMsg(message);
        result.setModel(model);
        return result;
    }

    public <T> Result<T> successResponse(T model) {
        return response(ResultCode.SUCCESS.getVal(), ResultCode.SUCCESS.name(), model);
    }

    public <T> Result<T> successResponse() {
        return response(ResultCode.SUCCESS.getVal(), ResultCode.SUCCESS.name(), null);
    }

    public <T> Result<T> failResponse(T model) {
        return response(ResultCode.FAIL.getVal(), ResultCode.FAIL.name(), model);
    }

    public <T> Result<T> failResponse(ResultCode code, String message) {
        return response(code.getVal(), message, null);
    }

    public <T> Result<T> failResponse() {
        return response(ResultCode.FAIL.getVal(), ResultCode.FAIL.name(), null);
    }
}
