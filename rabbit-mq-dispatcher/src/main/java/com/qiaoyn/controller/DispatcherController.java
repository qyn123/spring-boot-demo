package com.qiaoyn.controller;

import com.qiaoyn.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiaoyanan
 * date:2022/11/04 11:38
 */
@RestController
@RequestMapping("/dispatch")
public class DispatcherController {
    @Autowired
    private DispatchService dispatchService;

    public static final String ORDER_ID = "100001";

    // 添加订单后，添加骑手接单信息
    @GetMapping("/order")
    public String lock(String orderId) throws Exception {

        // 服务与服务之间的调用，可能会存在网络抖动，或者延迟
        if(orderId.equals(ORDER_ID)){
            // 模拟业务处理耗时。接口调用者会认为超时
            Thread.sleep(3000L);
        }
        dispatchService.dispatch(orderId);
        return "success";
    }
}
