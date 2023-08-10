package com.poly.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("serial")
//@AllArgsConstructor
//@NoArgsConstructor
@Data
@Entity
@Table(name = "Orderdetails")
public class OrderDetail implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double price;
    Integer quantity;
    @ManyToOne @JoinColumn(name = "Productid")
    Product product;
    @ManyToOne @JoinColumn(name = "Orderid")
    Order order;
}

