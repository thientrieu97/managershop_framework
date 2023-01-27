package com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass//tu tim hieu
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedDate//tu tim hieu
    @Column(name = "created_at", updatable = false)
    @JsonIgnore//tu tim hieu
    Date createdAt = new Date();

    @LastModifiedDate//tu tim hieu
    @Column(name = "update_at")
    @JsonIgnore
    Date updatedAt = new Date();

    @Column(name = "delete_at")
    @JsonIgnore
    Date deletedAt;

}
