package com.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Table(name = "user_role")
@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class UserRole extends BaseEntity {

    @Id
    @SequenceGenerator(name = "user_role_id_seq", sequenceName = "user_role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_id_seq")
    Integer id;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    String userId;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    String roleName;
}
