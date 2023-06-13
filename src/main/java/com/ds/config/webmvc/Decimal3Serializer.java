package com.ds.config.webmvc;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * @Description: 序列化 保留3位小数
 * @Author lt
 * @Date 2023/6/13 15:08
 */
public class Decimal3Serializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.writeNull();
            return;
        }
        BigDecimal bigDecimal = new BigDecimal(object.toString()).setScale(3, BigDecimal.ROUND_HALF_UP);
        System.out.println("============"+bigDecimal);
        serializer.write(bigDecimal.stripTrailingZeros().toPlainString());
    }
}
