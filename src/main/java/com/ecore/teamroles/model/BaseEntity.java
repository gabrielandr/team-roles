package com.ecore.teamroles.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Base entity with common fields
 *
 * @author andradegabriel
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @Version
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}