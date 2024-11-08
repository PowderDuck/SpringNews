package com.management.news.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseDto
{
    public boolean status = false;

    @JsonInclude(Include.NON_NULL)
    public String message = null;

    public final LocalDateTime submittedAt = LocalDateTime.now();
    public Object data;

    public ResponseDto(boolean _status, String _message, Object _data)
    {
        status = _status;
        message = _message;
        data = _data;
    }

    public static ResponseDto ok(boolean _status, String _message)
    {
        return new ResponseDto(_status, _message, null);
    }

    public static ResponseDto ok(boolean _status, Object _data)
    {
        return new ResponseDto(_status, null, _data);
    }

    public static ResponseDto ok(boolean _status)
    {
        return new ResponseDto(_status, null, null);
    }
}
