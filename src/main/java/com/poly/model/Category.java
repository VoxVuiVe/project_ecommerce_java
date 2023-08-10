package com.poly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable{
    @Id
//    @NotEmpty(message = "Mã danh mục không được để trống!")
    @Column(columnDefinition = "nvarchar(255)")
    String id;

    @Column(columnDefinition = "nvarchar(255)")
//    @NotEmpty(message = "Tên danh mục không được để trống!")
    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Product> products;
}
