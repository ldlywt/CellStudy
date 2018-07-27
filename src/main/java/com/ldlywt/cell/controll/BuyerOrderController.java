package com.ldlywt.cell.controll;

import com.ldlywt.cell.converter.OrderForm2OrderDTOConverter;
import com.ldlywt.cell.dto.OrderDTO;
import com.ldlywt.cell.emus.ResultEnum;
import com.ldlywt.cell.exception.SellException;
import com.ldlywt.cell.form.OrderForm;
import com.ldlywt.cell.service.BuyerService;
import com.ldlywt.cell.service.OrderService;
import com.ldlywt.cell.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/07/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public Result<Map<String,String>> create(@Valid OrderForm orderForm,
                                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDTO);
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return Result.SuccResult(map);
    }

    //订单列表
    @GetMapping("/list")
    public Result<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOS = orderService.findList(openid, pageRequest);

        return Result.SuccResult(orderDTOS);
    }

    //订单详情
    @GetMapping("/detail")
    public Result<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderOne = buyerService.findOrderOne(openid, orderId);
        return Result.SuccResult(orderOne);
    }

    //取消订单
    @PostMapping("/cancel")
    public Result cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return Result.SuccResult();
    }

}

