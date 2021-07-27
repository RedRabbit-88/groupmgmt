package com.app.web.group.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupCreateForm {

    @NotNull
    @Min(value = 1)
    private Integer groupCnt;

    @NotNull
    @Min(value = 1)
    private Integer memberCnt = 0;

    private List<Long> members = new ArrayList<>();

}
