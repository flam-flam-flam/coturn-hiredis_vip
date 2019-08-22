package com.ukelink.voip.UmCc.feign;

import com.ukelink.voip.UmCc.pojo.PushInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @InterfaceName PushFeign
 * @Description TODO
 * @Author chuang.gao
 * @Date 2019-8-20 14:11
 * @Version 1.0
 */
@FeignClient("UM-PUSH")
public interface PushFeign {
    @PostMapping(value = "/um/push/send")
    Object messagePush(PushInfo pushInfo);
}
