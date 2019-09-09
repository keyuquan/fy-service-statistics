package com.fy.service.statistics.model.Member;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员状态分析
 */
@Data
public class MemberStatusAnalysis {

    @ApiModelProperty("日期")
    private String create_date;

//================ 新增会员 ==================

    @ApiModelProperty("新增会员")
    private Integer new_member;

    @ApiModelProperty("当日新增充值会员")
    private Integer ltv_1;
    @ApiModelProperty("次日新增充值会员")
    private Integer ltv_2;
    @ApiModelProperty("3日新增充值会员")
    private Integer ltv_3;
    @ApiModelProperty("4日新增充值会员")
    private Integer ltv_4;
    @ApiModelProperty("5日新增充值会员")
    private Integer ltv_5;
    @ApiModelProperty("6日新增充值会员")
    private Integer ltv_6;
    @ApiModelProperty("7日新增充值会员")
    private Integer ltv_7;
    @ApiModelProperty("14日新增充值会员")
    private Integer ltv_14;
    @ApiModelProperty("30日新增充值会员")
    private Integer ltv_30;


    @ApiModelProperty("当日累计充值会员")
    private Integer ltv_all_1;
    @ApiModelProperty("次日累计充值会员")
    private Integer ltv_all_2;
    @ApiModelProperty("3日累计充值会员")
    private Integer ltv_all_3;
    @ApiModelProperty("4日累计充值会员")
    private Integer ltv_all_4;
    @ApiModelProperty("5日累计充值会员")
    private Integer ltv_all_5;
    @ApiModelProperty("6日累计充值会员")
    private Integer ltv_all_6;
    @ApiModelProperty("7日累计充值会员")
    private Integer ltv_all_7;
    @ApiModelProperty("14日累计充值会员")
    private Integer ltv_all_14;
    @ApiModelProperty("30日累计充值会员")
    private Integer ltv_all_30;


    @ApiModelProperty("当日累计充值转化率")
    private String ltv_1_rate;
    @ApiModelProperty("次日累计充值转化率")
    private String ltv_2_rate;
    @ApiModelProperty("3日累计充值转化率")
    private String ltv_3_rate;
    @ApiModelProperty("4日累计充值转化率")
    private String ltv_4_rate;
    @ApiModelProperty("5日累计充值转化率")
    private String ltv_5_rate;
    @ApiModelProperty("6日累计充值转化率")
    private String ltv_6_rate;
    @ApiModelProperty("7日累计充值转化率")
    private String ltv_7_rate;
    @ApiModelProperty("14日累计充值转化率")
    private String ltv_14_rate;
    @ApiModelProperty("30日累计充值转化率")
    private String ltv_30_rate;

//============== 活跃会员 ======================

    //=========新增会员活跃趋势
    @ApiModelProperty("活跃会员")
    private Integer active_member;

    @ApiModelProperty("活跃的新增会员")
    private Integer active_new_member;

    @ApiModelProperty("新增会员活跃率")
    private String activity_new_member_rate;


    //=========分时段活跃会员
    @ApiModelProperty("活跃会员1点钟活跃人数")
    private Integer active_member_1;

    @ApiModelProperty("活跃会员2点钟活跃人数")
    private Integer active_member_2;

    @ApiModelProperty("活跃会员3点钟活跃人数")
    private Integer active_member_3;

    @ApiModelProperty("活跃会员4点钟活跃人数")
    private Integer active_member_4;

    @ApiModelProperty("活跃会员5点钟活跃人数")
    private Integer active_member_5;

    @ApiModelProperty("活跃会员6点钟活跃人数")
    private Integer active_member_6;

    @ApiModelProperty("活跃会员7点钟活跃人数")
    private Integer active_member_7;

    @ApiModelProperty("活跃会员8点钟活跃人数")
    private Integer active_member_8;

    @ApiModelProperty("活跃会员9点钟活跃人数")
    private Integer active_member_9;

    @ApiModelProperty("活跃会员10点钟活跃人数")
    private Integer active_member_10;

    @ApiModelProperty("活跃会员11点钟活跃人数")
    private Integer active_member_11;

    @ApiModelProperty("活跃会员12点钟活跃人数")
    private Integer active_member_12;

    @ApiModelProperty("活跃会员13点钟活跃人数")
    private Integer active_member_13;

    @ApiModelProperty("活跃会员14点钟活跃人数")
    private Integer active_member_14;

    @ApiModelProperty("活跃会员15点钟活跃人数")
    private Integer active_member_15;

    @ApiModelProperty("活跃会员16点钟活跃人数")
    private Integer active_member_16;

    @ApiModelProperty("活跃会员17点钟活跃人数")
    private Integer active_member_17;

    @ApiModelProperty("活跃会员18点钟活跃人数")
    private Integer active_member_18;

    @ApiModelProperty("活跃会员19点钟活跃人数")
    private Integer active_member_19;

    @ApiModelProperty("活跃会员20点钟活跃人数")
    private Integer active_member_20;

    @ApiModelProperty("活跃会员21点钟活跃人数")
    private Integer active_member_21;

    @ApiModelProperty("活跃会员22点钟活跃人数")
    private Integer active_member_22;

    @ApiModelProperty("活跃会员23点钟活跃人数")
    private Integer active_member_23;

    @ApiModelProperty("活跃会员00点钟活跃人数")
    private Integer active_member_00;

    @ApiModelProperty("活跃会员00点到23点活跃人数合计")
    private Integer active_member_all;

    //=========活跃会员忠诚度
    @ApiModelProperty("7天活跃中≥3天的会员")
    private Integer active_member_loyalty;

//============== 有效会员 ======================

    @ApiModelProperty("会员总数")
    private Integer total_member;

    @ApiModelProperty("有效会员")
    private Integer effective_member;

    @ApiModelProperty("有效会员率")
    private String effective_member_rate;

//============== 流失会员 ======================

    @ApiModelProperty("流失会员")
    private Integer loss_member;

    @ApiModelProperty("未充值流失会员")

    private Integer not_charged_loss_member;

    @ApiModelProperty("未充值流失率")
    private String not_charged_loss_member_rate;

    @ApiModelProperty("首充流失会员")
    private Integer first_charge_loss_member;

    @ApiModelProperty("首充流失率")
    private String first_charge_loss_member_rate;

    @ApiModelProperty("二充流失会员")
    private Integer second_charge_loss_member;

    @ApiModelProperty("二充流失率")
    private String second_charge_loss_member_rate;

    @ApiModelProperty("大于二充流失会员")
    private Integer many_charge_loss_member;

    @ApiModelProperty("大于二充流失率")
    private String many_charge_loss_member_rate;


//============== 潜在流失会员 ======================
    @ApiModelProperty("潜在流失会员")
    private Integer potential_loss_member;

    @ApiModelProperty("潜在流失会员率")
    private String potential_loss_member_rate;

//============== 回归会员 ======================

    @ApiModelProperty("回归会员")
    private Integer returning_member;

    @ApiModelProperty("回归率")
    private String returning_member_rate;

//============== 留存会员 ======================

    @ApiModelProperty("次日留存")
    private Integer retained_member_1;

    @ApiModelProperty("3日留存")
    private Integer retained_member_3;

    @ApiModelProperty("7日留存")
    private Integer retained_member_7;

    @ApiModelProperty("14日留存")
    private Integer retained_member_14;

    @ApiModelProperty("30日留存")
    private Integer retained_member_30;



    @ApiModelProperty("次日留存率")
    private String retained_member_1_rate;

    @ApiModelProperty("3日留存率")
    private String retained_member_3_rate;

    @ApiModelProperty("7日留存率")
    private String retained_member_7_rate;

    @ApiModelProperty("14日留存率")
    private String retained_member_14_rate;

    @ApiModelProperty("30日留存率")
    private String retained_member_30_rate;



}
