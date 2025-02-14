package com.blackfiresoft.sheepmall.product;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;


@Entity
@Table(name = "category_tb")
@Data
public class CateGory implements Serializable {

    @Serial
    private static final long serialVersionUID = -8389612920521626342L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column(nullable = false)
    private String name;

}
