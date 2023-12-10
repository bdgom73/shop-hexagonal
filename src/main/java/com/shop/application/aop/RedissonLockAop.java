package com.shop.application.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class RedissonLockAop {
    private final Logger log = LoggerFactory.getLogger(RedissonLockAop.class);

    private final RedissonClient redissonClient;

    @Around("@annotation(com.shop.application.aop.RedissonLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);

        assert redissonLock.key() != null : "키 값을 입력 하세요";

        String key = redissonLock.key();

        /* get rLock 객체 */
        RLock rock = redissonClient.getLock(key);

        try {
            /* get lock */
            boolean isPossible = rock.tryLock(
                    redissonLock.waitTime(),
                    redissonLock.leaseTime(),
                    redissonLock.timeUnit()
            );

            if (!isPossible) {
                return false;
            }

            log.info("Redisson Lock Key : {}", key);

            /* service call */
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        } finally {
            rock.unlock();
        }
    }
}
