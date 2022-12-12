package com.ds.config.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NumberGenServiceImpl implements NumberGenService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final int LENGTH = 6;

    private static final String MONTH_FORMAT = "yyyyMM";

    private static final String DAY_FORMAT = "yyyyMMdd";

    public String generateNumber(String code) {
        return getNumber(code, "");
    }

    public String generateNumberByMonth(String code) {
        return getNumber(code, new SimpleDateFormat(MONTH_FORMAT).format(new Date()));
    }

    public String generateNumberByDay(String code) {
        return getNumber(code, new SimpleDateFormat(DAY_FORMAT).format(new Date()));
    }

    private String getNumber(String code, String month) {
        code += month;
        Long number = stringRedisTemplate.opsForValue().increment("" + ":" + code);
        return code + StringUtils.leftPad(number.toString(), LENGTH, '0');
    }

}
