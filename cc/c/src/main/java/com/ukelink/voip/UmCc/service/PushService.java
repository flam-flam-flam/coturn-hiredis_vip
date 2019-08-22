package com.ukelink.voip.UmCc.service;

/**
 * @InterfaceName PushService
 * @Description TODO
 * @Author chuang.gao
 * @Date 2019-8-20 14:41
 * @Version 1.0
 */
public interface PushService {
    void push(String logId, String account, String pushType, byte[] content);
}
