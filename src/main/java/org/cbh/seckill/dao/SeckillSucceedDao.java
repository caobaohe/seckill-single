package org.cbh.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.cbh.seckill.entity.SeckillSucceed;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/22.
 */
public interface SeckillSucceedDao {

    /**
     * 添加一条秒杀成功记录
     *
     * @param seckillId
     * @param userId
     * @return
     */
    int insertSeckillSucceed(@Param("seckillId") long seckillId, @Param("userId") long userId);

    /**
     * 根据id查询
     *
     * @param seckillId
     * @param userId
     * @return
     */
    SeckillSucceed queryByIdWidthSeckill(@Param("seckillId") long seckillId, @Param("userId") long userId);


}