package com.ukelink.voip.UmCc.common;

import lombok.Data;

@Data
public class BaseResponse<T> {

    /** 返回码 */
    private String code ="00000000";

    /** 返回说明 */
    private String msg ="success";

    /** 请求事件id */
    private String eventId;

    /** 服务器日志id */
    private String indexId;

    /** 返回时间 */
    private Long eventTime;

    /** 返回内容 */
    private T data;

}
