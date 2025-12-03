package com.ohgiraffers.datajpa.menu.controller;

import com.ohgiraffers.datajpa.menu.dto.MenuDTO;
import com.ohgiraffers.datajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
@Slf4j    // log라는 변수명으로 Logger 객체 타입의 필드가 선언되고 생성된다.
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public String findMenuByCode(@PathVariable int menuCode, Model model) {
        MenuDTO resultMenu = menuService.findMenuByMenuCode(menuCode);
        model.addAttribute("menu", resultMenu);
        return "menu/detail";
    }

    @GetMapping("/list")
    public String findMenuList(Model model) {

        return "menu/list";
    }
}