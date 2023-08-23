package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonB implements Serializable {

    private static final long serialVersionUID = -879183414135409248L;
    private String id;
    private List<PersonC> personList;
}
