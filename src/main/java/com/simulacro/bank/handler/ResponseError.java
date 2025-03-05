package com.simulacro.bank.handler;

import java.time.Instant;
import lombok.Data;

@Data
public class ResponseError {

    private Instant timeStamp;
    private String status = "error";
    private int statusCode = 400;
    private String error;
}
