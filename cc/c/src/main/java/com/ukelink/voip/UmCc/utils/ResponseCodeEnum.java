package com.ukelink.voip.UmCc.utils;

/**
 * 返回值code枚举类
 *
 * @author liyuan.liu
 * @date 2019-07-23 13:59
 */
public enum ResponseCodeEnum {

    /** 请求成功 */
    SUCCESS("00000000", "success"),

    /** 服务器异常 */
    ERROR("99999999", "error");

    private String code;

    private String msg;

    ResponseCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
