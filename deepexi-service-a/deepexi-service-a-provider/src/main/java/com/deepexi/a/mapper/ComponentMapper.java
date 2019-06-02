package com.deepexi.a.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.a.domain.eo.Component;
import com.deepexi.a.domain.eo.Product;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by donh on 2018/7/24.
 */
public interface ComponentMapper extends BaseMapper<Component> {

    List<Component> selectComponentVo(@Param("price") Integer price);

    Component selectBySeq(Serializable seq);
}
