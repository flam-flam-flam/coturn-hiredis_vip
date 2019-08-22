package com.ukelink.voip.UmCc.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @ClassName VmpHeader
 * @Description TODO
 * @Author chuang.gao
 * @Date 2019-8-20 14:51
 * @Version 1.0
 */
@Data
@Slf4j
public class VmpHeader {

    /** 标志位 */
    private Byte magic;

    /** 包头长度(高6位，低两位填充1） */
    private Byte headerLen;

    /** 压缩标志(1非压缩，2压缩) */
    private Byte encrypType;

    /** 加密算法(1 AES，2 RSA) */
    private Byte zipFlag;

    /** token字节长度 */
    private Byte tokenLen;

    /** 发送端使用的协议版本 */
    private Short version;

    /** 用户id */
    private Long userId;

    /** token */
    private String token;

    /** 消息路由到业务的id */
    private Short cgiId;

    /** 原始包体的长度 */
    private Integer lenOrgBody;

    /** 压缩后的包体长度 */
    private Integer lenCompressed;

    /**
     * 给输出流中写入vmpHeader和vmpBody
     *
     * @param outputStream 输出流
     * @param vmpHeader 要写入的header
     * @param body 要写入的body
     */
    public static void setVmpHeaderAndBody(OutputStream outputStream, VmpHeader vmpHeader,
                                           byte[] body) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        // 下列顺序不能乱
        dos.writeByte(vmpHeader.getMagic());
        dos.writeByte(vmpHeader.getHeaderLen() << 2 + 3);
        dos.writeByte(vmpHeader.getEncrypType() << 2 + vmpHeader.getZipFlag());
        dos.writeByte(vmpHeader.getTokenLen());
        dos.write(vmpHeader.getToken().getBytes());
        dos.writeShort(vmpHeader.getVersion());
        dos.writeLong(vmpHeader.getUserId());
        dos.writeShort(vmpHeader.getCgiId());
        dos.writeInt(body.length);
        dos.writeInt(body.length);
        dos.write(body);
    }

    /**
     * 从输入流中读取vmpHeader信息，并剩下vmpBody的流
     *
     * @param inputStream 输入流，header+body
     * @return vmpHeader信息
     */
    public static VmpHeader getVmpHeader(InputStream inputStream) throws IOException {
        DataInputStream dis = new DataInputStream(inputStream);
        VmpHeader vmpHeader = new VmpHeader();
        // 下列顺序不能乱
        vmpHeader.setMagic(dis.readByte());
        vmpHeader.setHeaderLen((byte) (dis.readByte() >>> 2));
        byte zipFlagAndEncrypType = dis.readByte();
        vmpHeader.setEncrypType((byte) (zipFlagAndEncrypType >>> 2 & 63));
        vmpHeader.setZipFlag((byte) (zipFlagAndEncrypType & 3));
        vmpHeader.setTokenLen(dis.readByte());
        byte[] tokenArray = new byte[vmpHeader.getTokenLen()];
        dis.read(tokenArray);
        vmpHeader.setToken(new String(tokenArray));
        vmpHeader.setVersion(dis.readShort());
        vmpHeader.setUserId(dis.readLong());
        vmpHeader.setCgiId(dis.readShort());
        vmpHeader.setLenOrgBody(dis.readInt());
        vmpHeader.setLenCompressed(dis.readInt());
        return vmpHeader;
    }

}
