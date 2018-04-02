package org.leach.scrumcards.util;


import org.leach.scrumcards.dto.Result;
import org.leach.scrumcards.dto.ResultCode;

/**
 * @author Leach
 * @date 2018/3/30
 */
public class ResultUtil {

    private static <T> Result<T> fillResult(ResultCode resultCode, String message, String debug, T model) {
        Result<T> result = new Result();
        result.setCode(resultCode.getVal());
        result.setMsg(message);
        result.setModel(model);
        return result;
    }

    public static <T> Result<T> getSuccess() {
        return fillResult(ResultCode.SUCCESS, ResultCode.SUCCESS.toString(), null, null);
    }

    public static <T> Result<T> getSuccess(T model) {
        return fillResult(ResultCode.SUCCESS, ResultCode.SUCCESS.toString(), null, model);
    }

    public static <T> Result<T> getFail(String debug) {
        return fillResult(ResultCode.FAIL, ResultCode.FAIL.toString(), debug, null);
    }

    public static <T> Result<T> get(ResultCode resultCode) {
        return fillResult(resultCode, resultCode.toString(), null, null);
    }
    
    public static <T> Result<T> get(ResultCode resultCode, String message) {
        return fillResult(resultCode, message, null, null);
    }

    public static <T> Result<T> get(ResultCode resultCode, T model) {
        return fillResult(resultCode, resultCode.toString(), null, model);
    }

}
