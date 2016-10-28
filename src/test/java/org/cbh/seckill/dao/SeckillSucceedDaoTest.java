package org.cbh.seckill.dao;

import org.cbh.seckill.entity.SeckillSucceed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/10/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillSucceedDaoTest {

    @Resource
    private SeckillSucceedDao seckillSucceedDao;

    @Test
    public void testInsertSeckillSucceed() throws Exception {
        int insertCount = seckillSucceedDao.insertSeckillSucceed(1000, 1322345565);
        System.out.println(insertCount);
    }

    @Test
    public void testQueryByIdWidthSeckill() throws Exception {
        SeckillSucceed seckillSucceed = seckillSucceedDao.queryByIdWidthSeckill(1000, 1322345565);
        System.out.println(seckillSucceed);
    }
}