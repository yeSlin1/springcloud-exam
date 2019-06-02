package com.deepexi.a.service;

import com.deepexi.a.domain.eo.Component;
import com.deepexi.util.pageHelper.PageBean;

public interface ComponentService {

    PageBean getComponentList(Integer page, Integer size, Integer price);

    Component getComponentById(String id);

    Integer createComponent(Component Component);

    Boolean deleteComponentById(String id);

    void testError();
}
