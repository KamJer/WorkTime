package com.my.TimeWork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @Null(message = "ID must be null for creation", groups = CreateGroup.class)
    private Long id;

    @NotBlank(message = "Name is required", groups = CreateGroup.class)
    @Size(min = 1, max = 128, message = "Name must be between 1 and 128 characters", groups = CreateGroup.class)
    private String name;

    public interface CreateGroup {}
}
