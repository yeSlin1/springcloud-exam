package com.deepexi.a.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.deepexi.a.depend.DemoClient;
import com.deepexi.a.domain.eo.Component;
import com.deepexi.a.extension.ApplicationException;
import com.deepexi.a.service.ComponentService;
import com.deepexi.util.config.Payload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "ComponentController",description = "组件管理")
@RestController
@RequestMapping("/api/v1/components")
public class ComponentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DemoClient demolient; // feign让跨服务调用能够看起来像本地调用

    @Autowired
    private ComponentService ComponentService;

    @ApiOperation(value ="根据条件查询获取组件",notes ="",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "size",value = "每页查询数",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "name",value = "组件名称",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "status",value = "状态",required = false,dataType = "Integer"),
            @ApiImplicitParam(name = "type",value = "分类",required = false,dataType = "Integer")
    })
    @GetMapping
    public Payload getComponentList(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                  @RequestParam(name = "price", required = false, defaultValue = "1") Integer price) {
        return new Payload(ComponentService.getComponentList(page, size, price));
    }

    @GetMapping("/{seq:[a-zA-Z0-9]+}")
    public Payload getComponentById(@PathVariable("seq") String seq) {
        return new Payload(ComponentService.getComponentById(seq));
    }

    @PostMapping
    public Payload createComponent(@RequestBody Component Component) {
        return new Payload(ComponentService.createComponent(Component));
    }

    @PutMapping("/{id:[a-zA-Z0-9]+}")
    public Payload updateComponentById(@PathVariable("id") String id, Component Component) {
        return new Payload(null);
    }

    @DeleteMapping("/{id:[a-zA-Z0-9]+}")
    public Payload deleteComponentById(@PathVariable("id") String id) {
        return new Payload(ComponentService.deleteComponentById(id));
    }

    @GetMapping("/testError")
    public Payload testError() {
        ComponentService.testError();
        return new Payload(true);
    }

    @GetMapping("/testSentinel")
    @SentinelResource(value = "testSentinel", blockHandler = "exceptionHandler")
    public Payload testSentinel() {
        logger.info("远程Sentinel测试接口成功: Hello World!!");
        return new Payload(true);
    }

    /**
     * 熔断降级处理逻辑
     * @param s
     * @param ex
     * @return
     */
    public Payload exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        logger.info("-------------熔断降级处理逻辑---------\n");
        throw new ApplicationException("100", "熔断降级处理");
    }

    @GetMapping("/testFeign")
    public Payload testFeign(@RequestParam Integer a, @RequestParam Integer b) {
        logger.info("远程调用成功: Hello World!!");
        return new Payload(demolient.add(10, 20));
    }
}
