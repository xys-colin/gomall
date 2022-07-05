package cn.qmulin.gomall.order.web;

import cn.qmulin.gomall.order.service.OrderService;
import cn.qmulin.gomall.order.vo.OrderConfirmVo;
import cn.qmulin.gomall.order.vo.OrderSubmitVo;
import cn.qmulin.gomall.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 15:57
 */
@Controller
public class OrderWebController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo orderConfirmVo = orderService.confirmOrder();
        model.addAttribute("orderConfirmData",orderConfirmVo);
        return "confirm";
    }
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo,Model model){
        SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);
        if (responseVo.getCode()==0){
            //下单成功来到支付选项
            model.addAttribute("submitOrderResp",responseVo);
            return "pay";
        }else {
            return "redirect:http://order.gomall.com/toTrade";
        }
    }
}
