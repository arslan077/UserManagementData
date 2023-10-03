package com.user.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CustomResponseEntity {
    private User user;
    private List contacts;
    private List vehicle;
}
