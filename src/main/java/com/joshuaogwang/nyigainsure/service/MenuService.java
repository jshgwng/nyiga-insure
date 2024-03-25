package com.joshuaogwang.nyigainsure.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.joshuaogwang.nyigainsure.repository.MenuRepository;

public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public MenuRepository getMenuRepository() {
        return menuRepository;
    }

    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
}
