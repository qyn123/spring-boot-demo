package com.qiaoyn.xadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiaoyanan
 * date:2022/10/08 16:48
 */
@Controller
public class EchartController {

    @GetMapping("/echarts1")
    public String echarts1() {
        return "/echart/echarts1";
    }

    @GetMapping("/echarts2")
    public String echarts2() {
        return "/echart/echarts2";
    }

    @GetMapping("/echarts3")
    public String echarts3() {
        return "/echart/echarts3";
    }

    @GetMapping("/echarts4")
    public String echarts4() {
        return "/echart/echarts4";
    }

    @GetMapping("/echarts5")
    public String echarts5() {
        return "/echart/echarts5";
    }

    @GetMapping("/echarts6")
    public String echarts6() {
        return "/echart/echarts6";
    }

    @GetMapping("/echarts7")
    public String echarts7() {
        return "/echart/echarts7";
    }

    @GetMapping("/echarts8")
    public String echarts8() {
        return "/echart/echarts8";
    }

}
