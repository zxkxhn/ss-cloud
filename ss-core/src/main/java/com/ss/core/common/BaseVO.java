package com.ss.core.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用 Do 类
 *
 * @author zhangxk
 * @Email 980137428@qq.com
 * Date:   2019年11月30日 21:01
 */
@Getter
@Setter
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 5702408728946256694L;

    /**
     * todo 等shardspare-jdbc 是否兼容jdk 1.8 替换成 localDateTime 线程安全
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
}
