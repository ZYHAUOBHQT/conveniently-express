package com.neng.web.rest;

import com.neng.config.ApiConf;
import com.neng.pojo.Need;
import com.neng.pojo.Order;
import com.neng.pojo.User;
import com.neng.repository.NeedRepository;
import com.neng.repository.UserRepository;
import com.neng.service.inner.NeedService;
import com.neng.service.inner.OrderService;
import com.neng.service.inner.UserService;
import com.neng.utils.PageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;

/**
 * Created by nengneng on 2017/6/5.
 */

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);


    private UserService userService;
    private OrderService orderService;
    private NeedService needService;
    private UserRepository userRepository;
    private NeedRepository needRepository;

    @Autowired
    public AdminController(UserService userService,
                           OrderService orderService,
                           NeedService needService,
                           UserRepository userRepository,
                           NeedRepository needRepository){
        this.userService = userService;
        this.orderService = orderService;
        this.needService = needService;
        this.userRepository = userRepository;
        this.needRepository = needRepository;
    }

    @GetMapping("/")
    public String admin(Model model){

        model.addAttribute(ApiConf.view_content, ApiConf.fontend);
        model.addAttribute(ApiConf.breadCump, ApiConf.index);
        return "index";
    }

    @GetMapping(value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public String users(@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.ASC)Pageable pageable, Model model){

        Page<User> users = userService.list(pageable);
        PageWrapper<User> page = new PageWrapper<User>(users, ApiConf.users);
        model.addAttribute(ApiConf.view_content, ApiConf.user_list);
        model.addAttribute(ApiConf.breadCump, ApiConf.user);
        model.addAttribute(ApiConf.page,page);
        return ApiConf.index;
    }

    @GetMapping(value = "/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public String orders(@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.ASC)Pageable pageable, Model model){

        Page<Order> orders = orderService.list(pageable);
        PageWrapper<Order> page = new PageWrapper<Order>(orders, ApiConf.orders);
        model.addAttribute(ApiConf.view_content, ApiConf.order_list);
        model.addAttribute(ApiConf.breadCump, ApiConf.order);
        model.addAttribute(ApiConf.page,page);
        return ApiConf.index;
    }

    @GetMapping(value = "/needs",produces = MediaType.APPLICATION_JSON_VALUE)
    public String needs(@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.ASC)Pageable pageable, Model model){

        Page<Need> needs = needService.list(pageable);
        PageWrapper<Need> page = new PageWrapper<Need>(needs, ApiConf.needs);
        model.addAttribute(ApiConf.view_content, ApiConf.need_list);
        model.addAttribute(ApiConf.breadCump, ApiConf.need);
        model.addAttribute(ApiConf.page,page);
        return ApiConf.index;
    }

    @GetMapping(value = ApiConf.changeUserStatus,produces = MediaType.APPLICATION_JSON_VALUE)
    public String changeStatus(@PathVariable("userId") Long userId,@PathVariable("status") Integer status){
        userService.changeStatus(userId, status);
        return "redirect:/" + ApiConf.users;
    }

    @GetMapping(value = ApiConf.models,produces = MediaType.APPLICATION_JSON_VALUE)
    public String modelList(Model model){
        model.addAttribute(ApiConf.view_content, ApiConf.model_info);
        model.addAttribute(ApiConf.breadCump, ApiConf.infoManagers);
        return ApiConf.index;
    }

    @PostMapping(value = ApiConf.user_add, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public String user_add(User user) {
        userRepository.save(user);
        return "redirect:/" + ApiConf.users;
    }



    @PostMapping(value = ApiConf.need_add, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public String need_add(Need need) {
        User user = userRepository.findOne(9L);
        need.setUser(user);
        needRepository.save(need);
        return "redirect:/" + ApiConf.needs;
    }

}
