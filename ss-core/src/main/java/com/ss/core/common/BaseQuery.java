package com.ss.core.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class BaseQuery implements Serializable {
    private static final long serialVersionUID = -8328150338715866548L;

    /**
     * 分页查询的参数，当前页数
     */
    @ApiModelProperty(value = "当前页数", example = "1")
    private long current = 1;
    /**
     * 分页查询的参数，当前页面每页显示的数量
     */
    @ApiModelProperty(value = "分页大小", example = "20")
    private long size = 20;

    @ApiModelProperty(value = "排序字段")
    private Map<String, Boolean> orderItem;
}
