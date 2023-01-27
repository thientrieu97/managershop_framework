package com.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public abstract class BaseDTO {

    @Null(groups = Create.class)
    @NotNull(groups = Nested.class)
    @Positive(groups = Nested.class)
    protected Integer id;

    public interface Create {
    }

    public interface Update {
    }

    public interface Nested {
    }
}