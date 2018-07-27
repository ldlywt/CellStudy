package com.ldlywt.cell.service;

import com.ldlywt.cell.dto.OrderDTO;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/07/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface BuyerService {
    OrderDTO findOrderOne(String openid, String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
