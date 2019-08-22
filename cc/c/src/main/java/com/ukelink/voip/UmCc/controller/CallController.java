package com.ukelink.voip.UmCc.controller;

import com.ukelink.voip.UmCc.bean.VmpHeader;
import com.ukelink.voip.UmCc.service.PushService;
import com.ukelink.voip.UmCc.utils.BaseResponseUtils;
import com.ukelink.voip.UmCc.utils.IdTools;
import com.ukelink.voip.UmCc.utils.ResponseCodeEnum;
import com.ukelink.voip.proto.Baseresponse;
import com.ukelink.voip.proto.Call;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName CallController
 * @Description TODO
 * @Author chuang.gao
 * @Date 2019-8-20 14:45
 * @Version 1.0
 */
@RestController
@Slf4j
public class CallController {
    @Autowired
    private PushService pushService;
    @PostMapping("/um/cc/invite")
    public void acceptInvite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lid = IdTools.getId1();
        VmpHeader vmpHeader = VmpHeader.getVmpHeader(request.getInputStream());
        Call.CallRequest.Builder callRequest = Call.CallRequest.parseFrom(request.getInputStream()).toBuilder();
        log.info("[lid:{}] insertGroupMember() requestBody: {}", lid, callRequest);

//        List<Clientinfo.ClientInfo> lists = callRequest.getData().getToUserList();
//        if (null != lists) {
//            for (Clientinfo.ClientInfo clientInfo : lists) {
//                pushService.push(lid,String.valueOf(clientInfo.getUserValue().getId()),"",callRequest.toByteArray());
//            }
//        }
        ByteArrayOutputStream pushStream = new ByteArrayOutputStream();
       Call.CallInfo.Builder callInfo = callRequest.getCallInfo().toBuilder();
       callInfo.setIndexId(lid);

        callRequest.getCallInfo().toBuilder().setIndexId(lid).build();

        String callId = callRequest.getCallInfo().getCallId();
        if( callId == null || callId.length()== 0)
        {
            callId = IdTools.getId1();
            callInfo.setCallId(callId);
//            callRequest.getCallInfo().toBuilder().setCallId(callId).build();
        }
        callRequest.setCallInfo(callInfo);
//        callRequest.build();
//        log.info("[lid66:{}] callRequest66:{}",lid,callInfo.toString());
        vmpHeader.setUserId(callRequest.getCallInfo().getToUser().getUserValue().getId());
        log.info("[lid55:{}] callRequest55:{}",lid,callRequest.toString());
        VmpHeader.setVmpHeaderAndBody(pushStream,vmpHeader,callRequest.build().toByteArray());
//        log.info("[lid:{}]",lid);
        pushService.push(lid,String.valueOf(callRequest.getCallInfo().getToUser().getUserValue().getId()),"505",pushStream.toByteArray());
        Baseresponse.BaseResponse baseResponse;
        baseResponse =  BaseResponseUtils
                .getBaseResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg(),
                        callRequest.getCallInfo().getOriginalId(), lid);

        Call.CallResponse callResponse;// = Call.CallResponse.newBuilder().setBaseResponse(baseResponse).build();
        callResponse = Call.CallResponse.newBuilder().setBaseResponse(baseResponse).setCallId(callId).build();
        vmpHeader.setUserId(callRequest.getCallInfo().getFromUser().getUserValue().getId());
        VmpHeader.setVmpHeaderAndBody(response.getOutputStream(), vmpHeader, callResponse.toByteArray());

    }
}
