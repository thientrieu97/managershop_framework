package com.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class BaseBusinessObject {

    @JsonProperty(namespace = "created_at")
    private ZonedDateTime createdAt;

    @JsonProperty(namespace = "updated_at")
    private ZonedDateTime updatedAt;

}
