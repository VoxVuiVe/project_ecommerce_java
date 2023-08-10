package com.poly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Report {
    @Id
    Serializable group;
    Long sum;
    Long count;

    public Report(Serializable group, Long sum, Long count) {
        this.group = group;
        this.sum = sum;
        this.count = count;
    }

    public Report() {
    }
}
