package com.poly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    @NotEmpty(message = "Tên đăng nhập không được để trống!")
    private String username;

    @NotEmpty(message = "Mật khẩu không được để trống!")
    @Size(min = 6, max = 14, message = "Mật khẩu phải lớn hơn 6 hoặc 14 kí tự")
    private String password;

    @NotEmpty(message = "Tên không được để trống!")
    @Column(columnDefinition = "nvarchar(255)")
    private String fullname;

    @NotEmpty(message = "Email không được để trống!")
    @Column(columnDefinition = "varchar(255)")
    private String email;

    private String photo;

    private Boolean activated;
    private Boolean admin;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Order> orders;
}

