package com.neng.service.inner;

import com.neng.pojo.Need;
import com.neng.pojo.Order;
import com.neng.pojo.OrderItems;
import org.springframework.http.ResponseEntity;

/**
 * Created by nengneng on 2017/7/13.
 */
public interface OrderItemsService {


    ResponseEntity<?> saveAndFlush(OrderItems orderItems);

    ResponseEntity<?> getOne(long orderItemsId);

    ResponseEntity<?> getByOrder(Order order);

    ResponseEntity<?> getByOrderId(Long id);
}
