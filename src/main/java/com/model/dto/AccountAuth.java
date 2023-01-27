package com.model.dto;

import com.dslplatform.json.JsonAttribute;
import com.model.bo.StatusCommon;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountAuth {

    private String username;

    @JsonAttribute(name = "account_id")
    private String accountId;

    private StatusCommon status;

    private String email;

    private String phone;
}
