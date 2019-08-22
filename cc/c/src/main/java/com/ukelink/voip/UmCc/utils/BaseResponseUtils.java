package com.ukelink.voip.UmCc.utils;

import com.ukelink.voip.UmCc.bean.VmpHeader;
import com.ukelink.voip.UmCc.common.BaseResponse;
import com.ukelink.voip.proto.Baseresponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于返回baseResponse对象时需要的一些操作
 *
 * @author liyuan.liu
 * @date 2019-07-23 13:54
 */
public class BaseResponseUtils {

    private BaseResponseUtils() {}

    /**
     * 设置返回对象的属性，系统内部调用使用
     *
     * @param code 返回码
     * @param msg 返回说明
     * @param eventId 请求事件id
     * @param indexId 服务器日志id
     * @param data 返回内容
     * @param <T> 返回内容类型
     * @param baseResponse 返回对象
     */
    public static <T> void setBaseResponse(String code, String msg, String eventId, String indexId, T data, BaseResponse<T> baseResponse) {
        baseResponse.setCode(code);
        baseResponse.setMsg(msg);
        baseResponse.setEventId(eventId);
        baseResponse.setIndexId(indexId);
        baseResponse.setEventTime(System.currentTimeMillis());
        baseResponse.setData(data);
    }

    /**
     * 获取返回的基础对象，用于构建protobuf对象时使用
     *
     * @param code 返回码
     * @param msg 返回说明
     * @param eventId 请求事件id
     * @param indexId 服务器日志id
     * @return 返回对象
     */
    public static Baseresponse.BaseResponse getBaseResponse(String code, String msg, String eventId, String indexId) {
        Baseresponse.BaseResponse.Builder baseResponseBuilder = Baseresponse.BaseResponse.newBuilder();
        baseResponseBuilder.setCode(code);
        baseResponseBuilder.setMsg(msg);
        baseResponseBuilder.setEventId(eventId);
        baseResponseBuilder.setIndexId(indexId);
        baseResponseBuilder.setEventTime(System.currentTimeMillis());
        return baseResponseBuilder.build();
    }


    /**
     * 用于将vmpHeader和vmpBody拼接起来，并接入接口的返回输出流中
     *
     * @param response 接口的返回对象
     * @param vmpHeader vmp头信息
     * @param vmpBody vmp体
     * @throws IOException 异常
     */
    public static void writeBodyToOutputStream(HttpServletResponse response, VmpHeader vmpHeader, byte[] vmpBody)
        throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 给输出流中写入vmpHeader和vmpBody
        VmpHeader.setVmpHeaderAndBody(byteArrayOutputStream, vmpHeader, vmpBody);
        // 将输出流写入返回响应体中
        byteArrayOutputStream.writeTo(response.getOutputStream());
    }

}
