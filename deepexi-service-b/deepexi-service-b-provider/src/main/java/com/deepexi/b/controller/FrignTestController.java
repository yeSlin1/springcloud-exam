package com.deepexi.b.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.deepexi.b.depend.DemoClient;
import com.deepexi.b.depend.FrignClient;
import com.deepexi.b.domain.eo.Product;
import com.deepexi.b.extension.ApplicationException;
import com.deepexi.b.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deepexi.util.config.Payload;


/**
 * Created by donh on 2018/11/5.
 */
@Api(value = "FrignTestController",description = "远程调用服务测试")
@RestController
@RequestMapping("/api/v1/testFeign")
public class FrignTestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FrignClient frignClient; // feign让跨服务调用能够看起来像本地调用

    @GetMapping("/queryComponent")
    public Payload queryComponent(@RequestParam String seq) {
        logger.info("远程调用成功: Hello World!!");
        return new Payload(frignClient.queryComponent(seq));
    }
}
