package com.deepexi.b.depend;

import com.deepexi.b.domain.eo.Component;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by donh on 2018/11/5.
 */
@FeignClient("deepexi-service-a-provider")
public interface FrignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/deepexi-service-a/api/v1/components/")
    Component queryComponent(@PathVariable("seq") String seq);
}