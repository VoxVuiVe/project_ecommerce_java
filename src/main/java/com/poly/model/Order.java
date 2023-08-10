package com.poly.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
//@AllArgsConstructor
//@NoArgsConstructor
@Data
@Entity
@Table(name = "Orders")
public class Order implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(columnDefinition = "nvarchar(255)")
    String address;
    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    Date createDate = new Date();
    @ManyToOne @JoinColumn(name = "Username")
    Account account;
    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;
}
