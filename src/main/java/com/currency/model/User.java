package com.currency.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private Integer customerTenure = 0;
    private UserType userType;

}
