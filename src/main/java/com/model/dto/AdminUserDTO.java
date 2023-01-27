package com.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
@Accessors(chain = true)
public class AdminUserDTO {

    private Long id;

    @NotBlank
//    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String userName;

    @Size(max = 50)
    private String fullName;
    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    private Integer age;

    private String address;

    private String identity;

    private String phone;

    private BigDecimal totalMoney;

    private Set<String> authorities;

    public AdminUserDTO() {
        // Empty constructor needed for Jackson.
    }
}
