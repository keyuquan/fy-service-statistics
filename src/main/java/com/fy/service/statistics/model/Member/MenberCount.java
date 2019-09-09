package com.fy.service.statistics.model.Member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 会员状态明细  + 总条数
 */
@Data
public class MenberCount {

    @ApiModelProperty("会员状态明细")
    private List<MemberDetails> memberdetails;

    @ApiModelProperty("总条数")
    private long totalCount;


}
