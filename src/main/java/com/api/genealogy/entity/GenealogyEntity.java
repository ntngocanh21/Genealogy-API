package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "genealogy")
public class GenealogyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "history")
    private String history;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP", updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastUpdated", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date lastUpdated;

}

