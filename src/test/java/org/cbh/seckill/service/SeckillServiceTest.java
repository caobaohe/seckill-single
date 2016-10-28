package org.cbh.seckill.service;

import org.cbh.seckill.dto.Exposer;
import org.cbh.seckill.dto.SeckillExecution;
import org.cbh.seckill.entity.Seckill;
import org.cbh.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2016/10/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        System.out.println(seckillList);
        logger.info("list={}", seckillList);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill = seckillService.getById(1000);
        System.out.println(seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1000);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        SeckillExecution execution = seckillService.executeSeckill(1000, 1122339, "002fb93bb4694181ef070598a78a42dc");
        logger.info("秒杀结果:{}", execution);
    }


    @Test
    public void testSeckill(){
        long seckillId = 1000;
        long userId = 12312311;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            try {
                SeckillExecution execution = seckillService.executeSeckill(seckillId, userId, exposer.getMd5());
                logger.info("秒杀结果：{}", execution);
            } catch (SeckillException e) {
               logger.error(e.getMessage());
            }
        }else{
            logger.warn("不能秒杀：{}", exposer);
        }
    }


    @Test
    public void testExecuteSeckillByProcedure(){
        long seckillId = 1000;
        long userId = 123232424;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            SeckillExecution execution = seckillService.executeSeckillByProcedure(seckillId, userId, exposer.getMd5());
            logger.info("execution={}", execution);
        }
    }
}