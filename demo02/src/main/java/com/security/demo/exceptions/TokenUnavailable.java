package com.security.demo.exceptions;

/**
 * @author ProgZhou
 * @createTime 2022/04/07
 */
public class TokenUnavailable extends Exception{
    public TokenUnavailable(){}

    public TokenUnavailable(String msg){
        super(msg);
    }

}
