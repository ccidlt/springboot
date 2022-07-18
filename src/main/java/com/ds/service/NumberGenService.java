package com.ds.service;

public interface NumberGenService {
    /**
     * 根据code⽣成编号
     * 例:NB000001
     * @param code 前缀
     * @return 编号
     */
    String generateNumber(String code);
    /**
     * 根据code及年⽉⽣成编号
     * 例⼦:NB201905000001
     * @param code 前缀
     * @return 编号
     */
    String generateNumberByMonth(String code);
    /**
     * 根据code及年⽉日⽣成编号
     * 例⼦:NB20190508000001
     * @param code 前缀
     * @return 编号
     */
    String generateNumberByDay(String code);
}
