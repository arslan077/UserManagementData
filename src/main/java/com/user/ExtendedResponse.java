package com.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ExtendedResponse<T> extends BaseResponse {

    private T Userdata;
}
