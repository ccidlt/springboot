package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 分页返回结果
 * @Author lt
 * @Date 2023/5/31 16:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResultSet<T> implements Serializable {
    private static final long serialVersionUID = -3664373031078072038L;

    private int pageNo;

    private int pageSize;

    private int totalRecords;

    private int totalPages;

    private List<T> list;
}
