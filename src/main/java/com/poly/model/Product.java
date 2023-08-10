package com.poly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "Products")
@NamedQuery(name = "getInventoryByCategory", query = "SELECT new Report(p.category, SUM(p.quantity), COUNT(p)) " +
        "FROM Product p GROUP BY p.category")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(columnDefinition = "nvarchar(255)")
    String name;

    String image;

    Double price;

    Integer quantity;

    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date createDate = new Date();

    Boolean available;

    @ManyToOne @JoinColumn(name = "Categoryid")
    Category category;

    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;

//    @Override
//    public String toString() {
//        return "=> ID: " + getId() + "=> Ten: " + getName() + "=> Gia: "  + getPrice() + "=> NgayTao: "  + getCreateDate();
//    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
