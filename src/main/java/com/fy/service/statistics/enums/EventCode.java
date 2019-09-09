package com.fy.service.statistics.enums;

/**
 * 预置的事件类型枚举类
 */
public enum EventCode {

    SK_BILL("sk_bill", "账单明细"),
    SK_REDBONUS("sk_redbonus", "发包明细"),
    SK_REDBONUS_GRAB("sk_redbonus_grab", "抢包明细"),
    SK_FINANCE_CASH_DRAWS("sk_finance_cash_draws", "提现明细"),
    SK_FINANCE_RECHARGE("sk_finance_recharge", "充值明细"),
    SK_USER_BASEINFO("sk_user_baseinfo", "用户明细"),
    ST_BILL("st_bill", "账单统计"),
    ST_REDBONUS("st_redbonus", "红包统计"),
    ST_DAY("st_day", "按天统计"),
    ST_DAY_MEMBER("st_day_member", "按天统计会员"),
    DETAILS_RECHARGE("details_recharge", "支付明细"),
    LTV_DETAILS("ltv_details", "会员明细"),
    MEMBER_RECHARGE_DETAILS("member_recharge_details", "会员充值明细"),
    LTV_DETAILS_HOUR("ltv_details_hour", "会员明细小时"),
    ACTIVE_MEMBER_DAO("active_member_dao", "活跃会员人数统计"),
    MEMBER_TASK_CHAIN_DAO("member_task_chain_dao", "有效、潜在流失、流失、回归、留存人数统计"),
    DATA_ANALYSIS("data_analysis", "ltv 汇总");

    private String value;
    private String name;

    EventCode(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据 name 获得value
     *
     * @param name
     * @return
     */
    public static String getValue(String name) {
        EventCode[] values = EventCode.values();
        for (EventCode dataType : values) {
            if (dataType.getName().equals(name)) {
                return dataType.getValue();
            }
        }
        return null;
    }

    /**
     * 根据value 获取名字
     *
     * @param value
     * @return
     */
    public static String getName(String value) {
        EventCode[] values = EventCode.values();
        for (EventCode dataType : values) {
            if (dataType.getValue().equals(value)) {
                return dataType.getName();
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
