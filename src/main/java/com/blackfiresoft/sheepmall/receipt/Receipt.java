package com.blackfiresoft.sheepmall.receipt;

import com.blackfiresoft.sheepmall.user.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;


@Entity
@Table(name = "receipt_tb")
@Data
public class Receipt implements Serializable {

    @Serial
    private static final long serialVersionUID = -657700327372956349L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String area;
    @Column(nullable = false)
    private String detailedAddress;
    private String zipCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users users;
}
