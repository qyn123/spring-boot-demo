package com.qiaoyn.upload.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author qiaoyanan
 * 将加和的ReasonCode值拆分
 */
public class SplitReasonCodeUtil {

    public static void main(String[] args) {
        String status = "";
        System.out.println(Arrays.stream(status.split(",")).map(s -> Integer.parseInt(s.trim())).reduce(Integer::sum).get());
    }

    private static String splitReasonCode(Integer reasonCode) {
        List<Integer> cal = cal(reasonCode);
        return StringUtils.join(cal, ",");
    }

    public static List<Integer> cal(int value) {
        LinkedList<Integer> result = new LinkedList<>();
        int v1 = value % 2;
        //如果不是2的整数倍，把1加上，然后减掉1
        if (v1 != 0) {
            result.add(1);
            value = value - 1;
        }
        if (value > 0) {
            //判断是不是2的幂次方
            int v2 = (value - 1) & value;
            //计算2的最大幂次方
            int mi = miMax(value, 0);
            if (v2 == 0) {
                //是2的幂次方直接返回最大的幂次方的值
                result.add(miValue(mi));
            } else {
                //不是则拼上最大的幂次方的值，剩下的同理推算，直至完成
                addValue(mi, value, result);
            }
        }
        return result;
    }

    /**
     * 累加值
     */
    private static void addValue(int mi, int value, LinkedList<Integer> result) {
        //计算最大幂次方的值
        int miValue = miValue(mi);
        result.add(miValue);
        //减掉最大幂次方的值
        value = value - miValue;
        if (value != 0) {
            //到最后一个了
            mi = miMax(value, 0);
            if (mi == 1) {
                result.add(2);
            } else {
                addValue(mi, value, result);
            }
        }
    }

    /**
     * 计算2的最大幂次方
     */
    private static int miMax(int value, int count) {
        int v = value / 2;
        count++;
        if (v == 1) {
            return count;
        } else {
            return miMax(v, count);
        }
    }

    /**
     * 计算2的幂次方的结果
     */
    private static int miValue(int min) {
        return 1 << min;
    }
}
