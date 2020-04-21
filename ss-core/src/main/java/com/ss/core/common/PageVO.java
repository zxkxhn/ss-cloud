package com.ss.core.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class PageVO<T> {
    private static final long serialVersionUID = -4131430072666231064L;

    PageVO() {

    }

    PageVO(List<T> records, long total, long size, long current) {
        this.current = current;
        this.records = records;
        this.total = total;
        this.size = size;
    }


    /**
     * 查询数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数", example = "0")
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "分页大小", example = "10")
    private long size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", example = "1")
    private long current = 1;
}
