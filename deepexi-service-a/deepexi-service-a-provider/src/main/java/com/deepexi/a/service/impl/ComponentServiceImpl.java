package com.deepexi.a.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.deepexi.a.domain.eo.Component;
import com.deepexi.a.extension.ApplicationException;
import com.deepexi.a.mapper.ComponentMapper;
import com.deepexi.a.service.ComponentService;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by donh on 2018/11/6.
 */
@Service
public class ComponentServiceImpl implements ComponentService {

    private static final Logger logger = LoggerFactory.getLogger(ComponentServiceImpl.class);

    @Autowired
    private ComponentMapper ComponentMapper;

    public PageBean getComponentList(Integer page, Integer size, Integer age) {
        PageHelper.startPage(page, size);
        List<Component> userTasks = ComponentMapper.selectComponentVo(age);
        return new PageBean<>(userTasks);
    }

    public Integer createComponent(Component Component) {
        return ComponentMapper.insert(Component);
    }

    public Boolean deleteComponentById(String id) {
        ComponentMapper.deleteById(id);
        return true;
    }

    @SentinelResource(value = "testSentinel", fallback = "doFallback", blockHandler = "exceptionHandler")
    public Component getComponentById(String seq) {
        return ComponentMapper.selectBySeq(seq);
    }

    public String doFallback(long i) {
        // Return fallback value.
        return "Oops, degraded";
    }

    /**
     * 熔断降级处理逻辑
     * @param s
     * @param ex
     * @return
     */
    public void exceptionHandler(long s, Exception ex) {
        // Do some log here.
        logger.info("-------------熔断降级处理逻辑---------\n");
        throw new ApplicationException("100", "熔断降级处理");
    }

    @Override
    public void testError() {
        throw new ApplicationException("100", "测试异常");
    }
}