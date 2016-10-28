package org.cbh.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.cbh.seckill.dao.SeckillDao;
import org.cbh.seckill.dao.SeckillSucceedDao;
import org.cbh.seckill.dao.cache.RedisDao;
import org.cbh.seckill.dto.Exposer;
import org.cbh.seckill.dto.SeckillExecution;
import org.cbh.seckill.entity.Seckill;
import org.cbh.seckill.entity.SeckillSucceed;
import org.cbh.seckill.enums.SeckillStateEnum;
import org.cbh.seckill.exception.RepeatKillException;
import org.cbh.seckill.exception.SeckillClosedException;
import org.cbh.seckill.exception.SeckillException;
import org.cbh.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/23.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    //用于混淆md5加密的盐值
    private final String slat = "SIUHAGIUNHJKCNSO$%^S*&S*&(*S(*Y(S*CHUIHASHI";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SeckillSucceedDao seckillSucceedDao;
    @Autowired
    private RedisDao redisDao;

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 8);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null){
                return new Exposer(false, seckillId);
            }else {
                redisDao.putSeckill(seckill);
            }
        }
        long now = new Date().getTime();
        long start = seckill.getStartTime().getTime();
        long end = seckill.getEndTime().getTime();
        if(now < start || now > end || seckill.getNumber() <= 0){
            return new Exposer(false, seckillId, now, start, end);
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userId, String md5) throws SeckillException, RepeatKillException, SeckillClosedException {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        Date killTime = new Date();
        try {
            int insertCount = seckillSucceedDao.insertSeckillSucceed(seckillId, userId);
            if(insertCount > 0){
                int updateCount = seckillDao.reduceNumber(seckillId, killTime);
                if(updateCount > 0){
                    SeckillSucceed seckillSucceed = seckillSucceedDao.queryByIdWidthSeckill(seckillId, userId);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, seckillSucceed);//秒杀成功
                }else {
                    throw new SeckillClosedException("seckill is colsed");//秒杀关闭
                }
            }else {
                throw new RepeatKillException("seckill repeated");//重复秒杀
            }
        } catch (SeckillClosedException e) {
            throw e;
        } catch (RepeatKillException e){
            throw e;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    public SeckillExecution executeSeckillByProcedure(long seckillId, long userId, String md5) {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        Date killTime = new Date();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("seckillId", seckillId);
            paramMap.put("userId", userId);
            paramMap.put("killTime", killTime);
            paramMap.put("result", null);
            seckillDao.seckillByProcedure(paramMap);
            int result = MapUtils.getInteger(paramMap, "result");
            if(result == 1){
                SeckillSucceed seckillSucceed = seckillSucceedDao.queryByIdWidthSeckill(seckillId, userId);
                return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, seckillSucceed);
            }else {
                return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
        }
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
