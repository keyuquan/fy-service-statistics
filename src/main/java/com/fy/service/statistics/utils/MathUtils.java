package com.fy.service.statistics.utils;

import java.math.BigDecimal;

public class MathUtils {

    /**
     * double 保留2位小数向上取整
     *
     * @param num
     * @return
     */
    public static BigDecimal toDecimalUp(double num) {
        return BigDecimal.valueOf(num).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * double 保留1位小数向上取整
     *
     * @param num
     * @return
     */
    public static BigDecimal toDecimalUpone(double num) {
        return BigDecimal.valueOf(num).setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * double 保留2位小数向上取整
     *
     * @param num
     * @return
     */
    public static BigDecimal toDecimalUp(String num) {
        return BigDecimal.valueOf(Double.valueOf(num)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
