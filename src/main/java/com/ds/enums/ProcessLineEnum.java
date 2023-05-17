package com.ds.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @Description:
 * @Author lt
 * @Date 2023/5/17 10:02
 */
@Getter
public enum ProcessLineEnum {

    BX_SQRTOXMJL ("BX_SQRTOXMJL", "申请人提交项目经理审批", "1", "待审核", "1", "项目经理待审核"),
    BX_XMJLTOZJL ("BX_XMJLTOZJL", "项目经理提交总经理审批", "2", "审核中", "2", "总经理待审核"),
    BX_ZJLTOCW ("BX_ZJLTOCW", "总经理提交财务审批", "2", "审核中", "3", "财务待审核"),
    BX_XMJLTOCW ("BX_XMJLTOCW", "项目经理提交财务审批", "2", "审核中", "4", "财务待审核"),
    BX_XMJLBH ("BX_XMJLBH", "项目经理驳回", "4", "已驳回", "5", "项目经理驳回"),
    BX_ZJLBH ("BX_ZJLBH", "总经理驳回", "4", "已驳回", "6", "总经理驳回"),
    BX_CWBH ("BX_CWBH", "财务驳回", "4", "已驳回", "7", "财务驳回"),
    BX_END ("BX_END", "结束", "3", "审核通过", "8", "审核通过");

    private String code;
    private String codeDesc;
    private String stateBig;
    private String stateBigDesc;
    private String stateSmall;
    private String stateSmallDesc;

    ProcessLineEnum(String code, String codeDesc, String stateBig, String stateBigDesc, String stateSmall, String stateSmallDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
        this.stateBig = stateBig;
        this.stateBigDesc = stateBigDesc;
        this.stateSmall = stateSmall;
        this.stateSmallDesc = stateSmallDesc;
    }

    public static ProcessLineEnum getProcessStateEnum(String code){
        for (ProcessLineEnum processLineEnum : ProcessLineEnum.values()) {
            if(StrUtil.equals(processLineEnum.getCode(), code)){
                return processLineEnum;
            }
        }
        return null;
    }

}
