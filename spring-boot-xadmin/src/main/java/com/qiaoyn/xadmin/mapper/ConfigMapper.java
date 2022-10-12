package com.qiaoyn.xadmin.mapper;

import com.qiaoyn.xadmin.entity.ConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qiaoyanan
 * date:2022/10/12 16:56
 */
public interface ConfigMapper {

    List<ConfigEntity> queryList(@Param("group") String group);
}
