package com.security.demo.exceptions;

/**
 * @author ProgZhou
 * @createTime 2022/04/06
 */
public class TokenUnavailable extends Exception{
    public TokenUnavailable(){}

    public TokenUnavailable(String message){
        super(message);
    }
}
