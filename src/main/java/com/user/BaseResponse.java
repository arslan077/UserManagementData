package com.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class BaseResponse {

    private int status_code;
    private String status_message;
    private String exception;
}
