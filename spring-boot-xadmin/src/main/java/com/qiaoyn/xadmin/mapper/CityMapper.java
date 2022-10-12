package com.qiaoyn.xadmin.mapper;

import com.qiaoyn.xadmin.entity.City;
import com.qiaoyn.xadmin.entity.Province;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/10/12 11:18
 */
public interface CityMapper {

    List<Province> queryProvinceList();

    List<City> queryCityList(@Param("key") String key);
}
