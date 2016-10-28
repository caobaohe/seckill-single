package org.cbh.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.cbh.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/22.
 */
public interface SeckillDao {

    /**
     * 根据id获取seckill
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 查询所有seckill
     *
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 减库存
     *
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     * 使用数据库存储过程秒杀
     * @param paramMap
     */
    void seckillByProcedure(Map<String, Object> paramMap);
}
