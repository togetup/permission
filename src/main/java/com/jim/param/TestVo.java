package com.jim.param;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 赵建龙
 * @date 2018/5/8
 */
@Getter
@Setter
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull(message = "id 不可以为空！")
    @Min(value = 0, message = "id 至少大于等于 0")
    @Max(value = 10, message = "id 不能大于 10")
    private Integer id;

    private List<String> str;
}