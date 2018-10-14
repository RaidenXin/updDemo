package com.huihuang.model;

/**
 * 数据包
 */
public class FrameUnit {

    private long unitId;
    private int unitCont;
    private int unitNo;
    private  byte[] data;
    private int dateSize;

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public int getUnitCont() {
        return unitCont;
    }

    public void setUnitCont(int unitCont) {
        this.unitCont = unitCont;
    }

    public int getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(int unitNo) {
        this.unitNo = unitNo;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getDateSize() {
        return dateSize;
    }

    public void setDateSize(int dateSize) {
        this.dateSize = dateSize;
    }
}