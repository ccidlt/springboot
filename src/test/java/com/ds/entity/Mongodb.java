package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description:
 * @Author lt
 * @Date 2023/5/8 10:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "test")
public class Mongodb {
    @Id
    private String id;
    private String name;
}
