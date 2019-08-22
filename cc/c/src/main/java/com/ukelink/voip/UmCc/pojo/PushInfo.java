package com.ukelink.voip.UmCc.pojo;

import lombok.Data;

/**
 * @ClassName PushInfo
 * @Description TODO
 * @Author chuang.gao
 * @Date 2019-8-20 14:33
 * @Version 1.0
 */
@Data
public class PushInfo {
    private String logIndex;
    private String uId;
    private String messageType;
    private byte[] content;
}
