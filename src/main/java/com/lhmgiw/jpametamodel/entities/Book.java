package com.lhmgiw.jpametamodel.entities;

import com.lhmgiw.jpametamodel.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 20, unique = true)
    private String code;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "author", length = 150)
    private String author;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
}
