package io.bioaitech.common.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 数据权限过滤
     */
    public static final String SQL_FILTER = "sql_filter";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    /**
     * 是
     */
    public static final Integer TRUE = 1;
    /**
     * 否
     */
    public static final Integer FALSE = 0;

    public static final String XLS = "xls";

    public static final String XLSX = "sourcefile/xlsx";
    /**
     * 未支付
     */
    public static final Integer UNPAID = 1;
    /**
     * 已支付
     */
    public static final Integer PAID = 2;
    /**
     * 已退款
     */
    public static final Integer REFUNDED = 3;

    /**
     * 过期5分钟
     */
    public static final Integer REDIS_EXPIRE_KAPTCHA = 300;

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * mapping
     */
    public enum Mapping {
        /**
         * 行数映射
         */
        A(1), B(2), C(3), D(4), E(5), F(6), G(7), H(8), I(9), J(10), K(11), L(12), M(13), N(14), O(15), P(16), Q(17), R(18), S(19), T(20), U(21), V(22), W(23), X(24), Y(25), Z(26);

        private int value;

        Mapping(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static Set<String> getStageList() {
        Set<String> list = new HashSet<>();
        list.add("0");
        list.add("0a");
        list.add("0is");
        list.add("I");
        list.add("IA");
        list.add("IA1");
        list.add("IA2");
        list.add("IA3");
        list.add("IA4");
        list.add("IB");
        list.add("IC");
        list.add("II");
        list.add("IIA");
        list.add("IIB");
        list.add("IIC");
        list.add("III");
        list.add("IIIA");
        list.add("IIIB");
        list.add("IIIC");
        list.add("IV");
        list.add("IVA");
        list.add("IVB");
        list.add("IVC");
        list.add("-");
        list.add("*");
        list.add("IB1");
        list.add("IB2");
        list.add("IIA1");
        list.add("IIA2");
        list.add("IIIC1");
        list.add("IIIC2");
        return list;
    }
}
