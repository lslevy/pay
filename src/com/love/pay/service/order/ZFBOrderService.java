package com.love.pay.service.order;

import com.love.pay.dao.order.ZFBOrderDao;
import com.love.pay.dto.Order;
import com.love.pay.dto.Orders;
import com.love.pay.entity.order.ZFBOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ZFBOrderService
 * author  liusheng
 * Thu Jan 12 14:48:16 CST 2017
 */
@Service("zFBOrderService")
public class ZFBOrderService {

	@Autowired
	private ZFBOrderDao zFBOrderServiceDao;


	/**
	 * 获取订单信息
	 * @param orderId
	 * @param type
	 * @return
	 */
	public Orders queryOrderByIdAndType(Long orderId, int type) {
		return zFBOrderServiceDao.queryOrderByIdAndType(orderId);
	}

	/**
	 * 修改订单状态
	 * @param orderId
	 * @param state
	 */
	public void updateState(Long orderId, int state) {}

	public Orders checkOrder(Long orderId) {
		Orders orders = null;
		orders = zFBOrderServiceDao.queryOrderByIdAndType(orderId);
		return orders;
	}
}