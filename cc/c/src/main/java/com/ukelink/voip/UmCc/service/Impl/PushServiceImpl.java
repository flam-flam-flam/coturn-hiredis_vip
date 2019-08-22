package com.ukelink.voip.UmCc.service.Impl;

import com.ukelink.voip.UmCc.feign.PushFeign;
import com.ukelink.voip.UmCc.pojo.PushInfo;
import com.ukelink.voip.UmCc.service.PushService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName PushServiceImpl
 * @Description TODO
 * @Author chuang.gao
 * @Date 2019-8-20 14:37
 * @Version 1.0
 */
@Service
@Slf4j
public class PushServiceImpl implements PushService {
    @Autowired
    private PushFeign pushFeign;

    @Override
    @Async
    public void push(String logId, String account, String pushType, byte[] content) {
        log.info("[logId:{}] doPush account:{} pushType:{}", logId,account,pushType);
        PushInfo pushInfo = new PushInfo();
        pushInfo.setLogIndex(logId);
        pushInfo.setMessageType(pushType);
        pushInfo.setUId(account);
        pushInfo.setContent(content);
        try {
            Object response = pushFeign.messagePush(pushInfo);
            log.info("[logId:{}] pushIm  res:[{}]", logId, response);
        } catch (Exception e) {
            log.error("[logId:{}] pushIm error:{}", logId, ExceptionUtils.getStackTrace(e));
        }
    }
}
