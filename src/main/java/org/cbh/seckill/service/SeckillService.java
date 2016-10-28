package org.cbh.seckill.service;

import org.cbh.seckill.dto.Exposer;
import org.cbh.seckill.dto.SeckillExecution;
import org.cbh.seckill.entity.Seckill;
import org.cbh.seckill.exception.RepeatKillException;
import org.cbh.seckill.exception.SeckillClosedException;
import org.cbh.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by Administrator on 2016/10/23.
 */
public interface SeckillService {

    /**
     * 秒杀列表
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 输出秒杀接口地址，秒杀开启时输出秒杀地址，否则输出系统时间和秒杀起止时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param userId
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userId, String md5) throws SeckillException, RepeatKillException, SeckillClosedException;


    /**
     * 使用存储过程执行秒杀
     * @param seckillId
     * @param userId
     * @param md5
     */
    SeckillExecution executeSeckillByProcedure(long seckillId, long userId, String md5);

}
