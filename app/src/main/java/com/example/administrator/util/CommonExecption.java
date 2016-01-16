package com.example.administrator.util;

/**
 * Created by Administrator on 2015/11/13.
 */
public class CommonExecption extends Exception{

    public CommonExecption(){
        super();
    }

    public CommonExecption(String message,Throwable cause){
        super(message,cause);
    }

    public CommonExecption(String message){
        super(message);
    }

    public CommonExecption(Throwable casuse){
        super(casuse);
    }
}
