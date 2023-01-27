package com.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.model.bo.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.validation.constraints.Email;
import java.time.LocalDate;


@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserUpdateInfoDTO extends BaseDTO{

    @Schema(description = "User fullName", example = "fullName")
    @JsonProperty("fullName")
    private String fullName;

    @Schema(description = "Image Key", example = "39393663393030622d336165342d333236372d626463302d653963323733353736363862.jpeg")
    @JsonProperty("imageKey")
    private String imageKey;

    @Email
    @Schema(description = "Email", example = "thucnb@rikkeisoft.com")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Age", example = "18")
    @JsonProperty("age")
    private Integer age;

    @Schema(description = "Gender", example = "MALE")
    @JsonProperty("gender")
    private Gender gender;

    @Schema(description = "User address", example = "Handico Tower, 21st Floor Pham Hung, Me Tri Ha, Nam Tu Liem, Hanoi")
    @JsonProperty("address")

    private String address;
    @Schema(description = "User identity", example = "1745678098")

    @JsonProperty("identity")
    private String identity;

    @Schema(description = "User phone number", example = "0978665323")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "User birthDay", example = "2022-09-18")
    @JsonProperty("birthDay")
    LocalDate birthDay;

    @JsonProperty("description")
    String description;

    @JsonProperty("facebook")
    String facebook;

    @JsonProperty("twitter")
    String twitter;
}
