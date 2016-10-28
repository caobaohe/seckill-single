package org.cbh.seckill.entity;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/22.
 */
public class SeckillSucceed {

    private long seckillId;
    private long userId;
    private Date createTime;
    private short state;

    private Seckill seckill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SeckillSucceedDao{" +
                "seckillId=" + seckillId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", state=" + state +
                ", seckill=" + seckill +
                '}';
    }
}
