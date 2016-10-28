package org.cbh.seckill.dto;

/**
 * Created by Administrator on 2016/10/23.
 */

import org.cbh.seckill.entity.SeckillSucceed;
import org.cbh.seckill.enums.SeckillStateEnum;

/**
 * 秒杀执行结果
 */
public class SeckillExecution {
    private long seckillId;
    private int state;
    private String stateInfo;
    private SeckillSucceed seckillSucceed;

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SeckillSucceed seckillSucceed) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.seckillSucceed = seckillSucceed;
    }

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SeckillSucceed getSeckillSucceed() {
        return seckillSucceed;
    }

    public void setSeckillSucceed(SeckillSucceed seckillSucceed) {
        this.seckillSucceed = seckillSucceed;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", seckillSucceed=" + seckillSucceed +
                '}';
    }
}
