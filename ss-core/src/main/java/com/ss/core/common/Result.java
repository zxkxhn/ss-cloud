package com.ss.core.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ss.core.exception.SystemErrorTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

//@ApiModel(value = "Result", description = "接口返回对象" )
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int SUCCESSFUL_CODE = SystemErrorTypeEnum.SUCCESS.getCode();
    public static final String SUCCESSFUL_MSG = SystemErrorTypeEnum.SUCCESS.getMsg();

    @ApiModelProperty(value = "code", required = true)
    private int code;
    @ApiModelProperty(value = "描述信息")
    private String msg;
    @ApiModelProperty(value = "结果")
    private T data;


    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(SystemErrorTypeEnum systemErrorTypeEnum) {
        this.code = systemErrorTypeEnum.getCode();
        this.msg = systemErrorTypeEnum.getMsg();
    }

    public Result(SystemErrorTypeEnum systemErrorTypeEnum, String msg) {
        this.code = systemErrorTypeEnum.getCode();
        this.msg = msg;
    }

    public Result(SystemErrorTypeEnum systemErrorTypeEnum, T data) {
        this.code = systemErrorTypeEnum.getCode();
        this.msg = systemErrorTypeEnum.getMsg();
        this.data = data;
    }

    public Result(SystemErrorTypeEnum systemErrorTypeEnum, String msg, T data) {
        this.code = systemErrorTypeEnum.getCode();
        this.msg = systemErrorTypeEnum.getMsg();
        this.data = data;
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<>(SystemErrorTypeEnum.SUCCESS);
    }


    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param
     * @return Result
     */
    public static <T> Result<PageVO<T>> page(List<T> records, IPage<?> iPage) {
        PageVO<T> pageVO = new PageVO<>(records, iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return Result.success(pageVO);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static <T> Result<T> fail() {
        return new Result<>(SystemErrorTypeEnum.SYSTEM_ERROR);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }


    public static <T> Result<T> fail(String msg) {
        return new Result<>(SystemErrorTypeEnum.SYSTEM_ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> fail(SystemErrorTypeEnum systemErrorTypeEnum) {
        return new Result<>(systemErrorTypeEnum.getCode(), systemErrorTypeEnum.getMsg(), null);
    }

    public static <T> Result<T> fail(SystemErrorTypeEnum systemErrorTypeEnum, T data) {
        return new Result<>(systemErrorTypeEnum.getCode(), systemErrorTypeEnum.getMsg(), data);
    }

    public static <T> Result<T> fail(SystemErrorTypeEnum systemErrorTypeEnum, String msg) {
        return new Result<>(systemErrorTypeEnum.getCode(), msg, null);
    }


    public void setData(T data) {
        this.data = data;
        this.code = SUCCESSFUL_CODE;
    }


}
