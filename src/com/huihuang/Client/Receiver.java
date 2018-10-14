package com.huihuang.Client;

import com.huihuang.model.FrameUnit;
import com.huihuang.service.Teacher;
import com.huihuang.utils.UdpUtils;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Receiver extends Thread{

    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] data = new byte[Teacher.FRAME_UNIT_MAX + 14];
    private ClientUI ui;

    private static final Map<Integer,FrameUnit> unit_Map = new HashMap<>();

    public Receiver(ClientUI ui){
        try {
            this.ui = ui;
            socket = new DatagramSocket(8888);
            packet = new DatagramPacket(data, Teacher.FRAME_UNIT_MAX + 14);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            long unitId = 0;
            while (true){
                socket.receive(packet);
                FrameUnit unit = parsePackage(data);
                long newUnitId = unit.getUnitId();
                if (newUnitId == unitId){
                    unit_Map.put(unit.getUnitNo(), unit);
                }else if(newUnitId > unitId){
                    unitId = newUnitId;
                    unit_Map.clear();
                    unit_Map.put(unit.getUnitNo(), unit);
                }
                processFrame();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void processFrame(){
        try{
            Set<Map.Entry<Integer, FrameUnit>> entries = unit_Map.entrySet();
            int unitCount = entries.iterator().next().getValue().getUnitCont();
            if (unit_Map.size() == unitCount){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                for (Map.Entry<Integer, FrameUnit> entry : entries) {
                    FrameUnit unit = entry.getValue();
                    baos.write(unit.getData());
                }
                unit_Map.clear();
                ui.updateIcon(UdpUtils.unZipData(baos.toByteArray()));
                baos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private FrameUnit parsePackage(byte[] data){
        FrameUnit unit = new FrameUnit();
        int len = data.length - 14;
        byte[] unitIdArr = new byte[8];
        long unitId = UdpUtils.bytes2Long(data,0);
        int unitNo = data[8];
        int unitCount = data[9];
        int dataSize = UdpUtils.bytes2Int(data,10);
        byte[] sourceData = new byte[len];
        System.arraycopy(data,14, sourceData,0,len);
        unit.setUnitId(unitId);
        unit.setUnitNo(unitNo);
        unit.setUnitCont(unitCount);
        unit.setDateSize(dataSize);
        unit.setData(sourceData);
        return unit;
    }
}
