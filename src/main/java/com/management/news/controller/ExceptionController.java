package com.management.news.controller;

import com.management.news.model.dto.ResponseDto;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController implements ErrorController {

    @RequestMapping("/error")
    public ResponseDto HandleException()
    {
        return ResponseDto.Ok(false);
    }
}
