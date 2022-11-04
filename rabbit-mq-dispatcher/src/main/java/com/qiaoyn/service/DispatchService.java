package com.qiaoyn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qiaoyanan
 * date:2022/11/04 11:39
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DispatchService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void dispatch(String orderId) throws Exception {
        String addSql = "insert into kd_Order(orderId, name,status)  values (?,?,?)";
        int count = jdbcTemplate.update(addSql, orderId, "骑手张飞","0");
        if(count!=1){
            throw new Exception("物流信息创建失败，原因【数据库插入失败】");
        }
    }
}
