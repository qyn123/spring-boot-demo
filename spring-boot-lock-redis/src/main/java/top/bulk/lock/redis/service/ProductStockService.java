package top.bulk.lock.redis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.bulk.lock.redis.entity.ProductStock;


/**
 * 产品库存表
(ProductStock)表服务接口
 *
 * @author qiaoyanan
 */
public interface ProductStockService extends IService<ProductStock> {

    /**
     * 减库存无锁
     * @param id id
     * @return str
     */
    String reduceStockNoLock(Integer id);

    /**
     * 减库存
     * @param id id
     * @return str
     */
    String reduceStock(Integer id);

    /**
     * 减库存 - 自己实现 redis 锁接口
     * @param id id
     * @return str
     */
    String reduceStockByMyLock(Integer id);
}

