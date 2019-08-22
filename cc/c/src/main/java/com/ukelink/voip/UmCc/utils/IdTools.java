package com.ukelink.voip.UmCc.utils;

import cn.hutool.core.util.IdUtil;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * id生成工具类
 *
 * @author liyuan.liu
 * @date 2019-07-02 13:53
 */
@Component
public class IdTools {

    private IdTools() {}

    private static Long workerId;

    private static Long datacenterId;

    @Value("${snowflake.workerId}")
    public void setWorkerId(Long workerId) {
        IdTools.workerId = workerId;
    }

    @Value("${snowflake.datacenterId}")
    public void setDatacenterId(Long datacenterId) {
        IdTools.datacenterId = datacenterId;
    }

    /**
     * MongoDB ID生成策略
     *
     * @return 24个字符组成的id
     */
    public static String getId1() {
        return IdUtil.objectId();
    }

    /**
     * Twitter的Snowflake生成策略
     *
     * @return 8个字节的id
     */
    public static Long getId2() {
        return IdUtil.getSnowflake(workerId, datacenterId).nextId();
    }

    /**
     * 13位时间戳+6位随机数
     *
     * @return 8个字节，19个数字的id
     */
    public static Long getId3() {
        return System.currentTimeMillis() * 1000000L + ThreadLocalRandom.current().nextInt(0, 1000000);
    }

}
