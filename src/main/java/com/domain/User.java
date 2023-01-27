package com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.bo.Gender;
import com.model.bo.StatusCommon;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A user.
 */
@Table(name = "users")
@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String ENTITY_NAME = "User";

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    String id;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(columnDefinition = "varchar(60)")
    String password;

    @Column(columnDefinition = "varchar(50)")
    String fullName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    String email;

    @Column(columnDefinition = "varchar(250)")
    String imageUrl;

    @Size(max = 20)
    @Column(columnDefinition = "varchar(250)")
    String activationKey;

    @Column(columnDefinition = "varchar(20) default 'ACTIVE'", nullable = false)
    @Enumerated(EnumType.STRING)
    StatusCommon status;

    @Column(columnDefinition = "int default 10")
    Integer age;

    @Column(columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(columnDefinition = "timestamp")
    LocalDate birthDay;

    @Column(columnDefinition = "varchar(50)")
    String address;

    @Column(columnDefinition = "varchar(50)")
    String identity;

    @Column(columnDefinition = "varchar(50)")
    String phone;

    @Column(columnDefinition = "varchar(50)")
    String role;

    @Column(columnDefinition = "decimal(15,2) default '0'")
    BigDecimal totalMoney;

    @NotNull
    @Column(columnDefinition = "varchar(50)")
    String userName;

    @Column(columnDefinition = "text")
    String description;

    @Column(columnDefinition = "varchar(250)")
    String facebook;

    @Column(columnDefinition = "varchar(250)")
    String twitter;
}
