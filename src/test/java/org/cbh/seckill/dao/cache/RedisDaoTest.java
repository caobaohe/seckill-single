package org.cbh.seckill.dao.cache;

import org.cbh.seckill.dao.SeckillDao;
import org.cbh.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/10/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() throws Exception {
        long killId = 1000;
        Seckill seckill = redisDao.getSeckill(killId);
        if(seckill == null){
            seckill = seckillDao.queryById(killId);
            if(seckill == null){
                System.out.println("seckill not exist:" + killId);
            }else {
                String result = redisDao.putSeckill(seckill);
                System.out.println("result=" + result);
            }
        }else{
            System.out.println(seckill);
        }
    }
}