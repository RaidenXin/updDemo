package com.huihuang.utils;

import com.huihuang.service.Teacher;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Upd工具类
 */
public class UdpUtils {

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截图并把截图转换成byte数组
     * @return
     */
    public static byte[] captureScreen(){
        BufferedImage image = robot.createScreenCapture(new Rectangle(1920,1080));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, "jpg", baos);
        } catch (Exception e){
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * 压缩
     * @param data
     * @return
     */
    public static byte[] zipData(byte[] data){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        try{
            zos.putNextEntry(new ZipEntry("one"));
            zos.write(data);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                zos.close();
                baos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }

    /**
     * 解压缩
     * @param zipData
     * @return
     */
    public static byte[] unZipData(byte[] zipData){
        ByteArrayInputStream bais = new ByteArrayInputStream(zipData);
        ZipInputStream zis = new ZipInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            zis.getNextEntry();
            int index = 0;
            byte[] bytes = new byte[1024];
            while ((index = zis.read(bytes)) != -1){
                baos.write(bytes,0,index);
            }
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                bais.close();
                zis.close();
                baos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  baos.toByteArray();
    }

    public static byte[] long2Bytes(long l){
        byte[] result = new byte[8];
        for (int i = 0;i < 8;i++){
            result[i] = (byte) (l >> i * 8);
        }
        return result;
    }

    public static long bytes2Long(byte[] data,int startIndex){
        long result = 0;
        for (int i = 0;i < 8; i++){
            result = result | ((long) (data[startIndex + i] & 0xFF) << i * 8);
        }
        return result;
    }

    public static byte[] int2Bytes(int i){
        byte[] result = new byte[4];
        for (int j = 0;j < 4; j++){
            result[j] = (byte) (i >> i * 8);
        }
        return result;
    }

    public static int bytes2Int(byte[] data,int startIndex){
        int result = 0;
        for (int i = 0;i < 4; i++){
            result = result | ((int) data[startIndex + i] & 0xFF << i * 8);
        }
        return result;
    }
}
