package org.cbh.seckill.exception;

/**
 * 秒杀相关业务异常
 * Created by Administrator on 2016/10/23.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
