package io.bioaitech.modules.bioaitech.vo.res;

import io.bioaitech.modules.bioaitech.entity.OrderDetailsEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author miaozhuang
 * @Description
 * @Date 2019/9/25
 */
@Data
public class OrderResVO implements Serializable {

    private static final long serialVersionUID = 6264251072174768566L;

    private List<OrderDetailsEntity> productList;

    private String id;
    /**
     * 用户名
     */
    private String username;

    private String email;
    /**
     *
     */
    private String buyerName;
    /**
     *
     */
    private String buyerPhone;
    /**
     *
     */
    private String buyerAddress;
    /**
     *
     */
    private Integer orderStatus;
    /**
     *
     */
    private Integer paymentStatus;
    /**
     * 发货状态
     */
    private Integer deliveryStatus;

    /**
     *
     */
    private BigDecimal payable;
    /**
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
