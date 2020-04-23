package com.ss.core.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

@Slf4j
@Data
public class BaseQuery<T> implements Serializable {
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
//
//    @ApiModelProperty(hidden = true)
//    public Page<T> page() {
//        Page<T> page = new Page<>(this.getCurrent(),this.getSize());
//        if(CollectionUtil.isEmpty(orderItem)){
//            return page;
//        }
//        List<OrderItem> orderItemList = new ArrayList<>();
//        orderItem.forEach((k,v)->{
//            if (StrUtil.isBlank(k) || v == null) {
//                return;
//            }
//            OrderItem orderItem = new OrderItem();
//            orderItem.setColumn(k);
//            orderItem.setAsc(v);
//            orderItemList.add(orderItem);
//        });
//        page.setOrders(orderItemList);
//        return page;
//    }
}
