package com.qiaoyn.service;

/**
 * @author qiaoyanan
 * date:2022/06/28 15:48
 */
public class DemoService {
    public String sayWhat;
    public String toWho;
    public DemoService(String sayWhat, String toWho){
        this.sayWhat = sayWhat;
        this.toWho = toWho;
    }
    public String say(){
        return this.sayWhat + "!  " + toWho;
    }
}
