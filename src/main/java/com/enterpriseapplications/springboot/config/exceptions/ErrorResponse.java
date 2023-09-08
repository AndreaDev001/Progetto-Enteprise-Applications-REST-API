package com.enterpriseapplications.springboot.config.exceptions;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date date;
    private String url;
    private String code;
    private String message;
}
