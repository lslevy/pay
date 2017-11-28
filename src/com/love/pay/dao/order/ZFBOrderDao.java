package com.love.pay.dao.order;

import com.love.pay.dao.BaseDao;
import com.love.pay.dto.Order;
import com.love.pay.dto.Orders;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * ZFBOrderDao
 * author  liusheng
 * Thu Jan 12 14:48:16 CST 2017
 */
@Repository("zFBOrderDao")
public class ZFBOrderDao extends BaseDao {


    /**获取订单信息*/
    public Orders queryOrderByIdAndType(Long orderId) {
        Map map = new HashMap();
        map.put("order_id", orderId);
        return getListFirst(namedParameterJdbcTemplate_account.query("select * from orders where id = :order_id ",map,new BeanPropertyRowMapper<>(Orders.class)));
    }

    /**更改订单状态*/
    public int updateState(Long orderid, int state) {
        return jdbcTemplate_account.update("update orders set state = ? where id = ?", state, orderid);
    }
}