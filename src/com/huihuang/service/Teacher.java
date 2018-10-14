package com.huihuang.service;

import com.huihuang.model.FrameUnit;
import com.huihuang.utils.UdpUtils;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 发送服务端
 */
public class Teacher {

    private static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket(9999);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static final  int FRAME_UNIT_MAX = 60 * 1024;

    public static void main(String[] args) {
        while(true){
            sendOneScreenData();
        }
    }

    private static void sendOneScreenData() {
        //获取截屏数据
        byte[] data = UdpUtils.captureScreen();
        data = UdpUtils.zipData(data);
        List<FrameUnit> units = splitFrame(data);
        sendUnits(units);
    }

    /**
     *
     * @param data
     * @return
     */
    private static List<FrameUnit> splitFrame(byte[] data) {
        List<FrameUnit> result = new ArrayList<>();
        int endSize = data.length % FRAME_UNIT_MAX;
        long frameId = System.currentTimeMillis();
        int dataSize = data.length;
        boolean b = endSize == 0;
        int size = b? data.length / FRAME_UNIT_MAX :data.length / FRAME_UNIT_MAX + 1;
        for (int i = 0; i < size; i++){
            byte[] buff;
            FrameUnit unit = new FrameUnit();
            if (!b && i == size -1){
                buff = new byte[endSize];
                System.arraycopy(data, i * FRAME_UNIT_MAX,buff, 0, endSize);
                unit.setDateSize(endSize);
            }else {
                buff = new byte[FRAME_UNIT_MAX];
                System.arraycopy(data, i * FRAME_UNIT_MAX,buff, 0, FRAME_UNIT_MAX);
                unit.setDateSize(FRAME_UNIT_MAX);
            }
            unit.setUnitId(frameId);
            unit.setUnitNo(i);
            unit.setUnitCont(size);
            unit.setData(buff);
            result.add(unit);
        }
        return result;
    }

    public static void sendUnits(List<FrameUnit> units){
        if (null == units || units.isEmpty()){
            return;
        }
        for (FrameUnit unit : units) {
            int dataSize = unit.getDateSize();
            byte[] data = new byte[dataSize + 14];
            long id = unit.getUnitId();
            byte[] idArr = UdpUtils.long2Bytes(id);
            System.arraycopy(idArr,0, data, 0, 8);
            int unitNo = unit.getUnitNo();
            data[8] = (byte) unitNo;
            int unitCount = unit.getUnitCont();
            data[9] = (byte) unitCount;
            byte[] dataSizeArr = UdpUtils.int2Bytes(dataSize);
            System.arraycopy(dataSizeArr, 0, data, 10, 4 );
            System.arraycopy(unit.getData(), 0,data,14, dataSize);
            DatagramPacket packet = new DatagramPacket(data, dataSize + 14);
            packet.setSocketAddress(new InetSocketAddress("192.168.19.255", 8888));
            try{
                socket.send(packet);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
