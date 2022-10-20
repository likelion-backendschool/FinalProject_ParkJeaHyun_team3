package com.ll.finalProject.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "권한이 존재하지 않습니다.")
public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException(String question_not_found) {
    }
}
