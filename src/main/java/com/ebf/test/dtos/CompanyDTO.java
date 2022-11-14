package com.ebf.test.dtos;

import java.io.Serializable;

public class CompanyDTO extends AbstractDTO implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
