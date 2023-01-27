package com.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "roles")
@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class Role extends BaseEntity {

    @NotNull
    // trong request truyền vào không có trường name, hoặc giá trị truyền vào là null thì kết quả bao loi
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String name;
}
