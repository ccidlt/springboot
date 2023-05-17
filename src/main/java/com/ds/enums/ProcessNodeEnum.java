package com.ds.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @Description:
 * @Author lt
 * @Date 2023/5/17 10:02
 */
@Getter
public enum ProcessNodeEnum {

    BX_SQR ("BX_SQR", "申请人"),
    BX_XMJL ("BX_XMJL", "项目经理"),
    BX_ZJL ("BX_ZJL", "总经理"),
    BX_CW ("BX_CW", "财务"),
    BX_END ("BX_END", "结束");

    private String code;
    private String codeDesc;

    ProcessNodeEnum(String code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    public static ProcessNodeEnum getProcessNodeEnum(String code){
        for (ProcessNodeEnum processNodeEnum : ProcessNodeEnum.values()) {
            if(StrUtil.equals(processNodeEnum.getCode(), code)){
                return processNodeEnum;
            }
        }
        return null;
    }

}
