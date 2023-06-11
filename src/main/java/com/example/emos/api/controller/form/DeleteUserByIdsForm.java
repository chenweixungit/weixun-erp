package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "删除用户表单类")
public class DeleteUserByIdsForm {
    @NotEmpty(message = "ids不能为空")
    @Schema(description = "用户ids")
    private Integer[] ids;
}
