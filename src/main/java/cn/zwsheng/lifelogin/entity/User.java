package cn.zwsheng.lifelogin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user",catalog = "life")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;
}
