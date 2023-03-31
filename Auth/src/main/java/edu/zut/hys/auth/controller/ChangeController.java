package edu.zut.hys.auth.controller;

import edu.zut.hys.auth.generator.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author Hys
 * Date 2022/3/2 22:42
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/change")
public class ChangeController {

    @Autowired
    RolePermissionService rolePermissionService;

    @RequestMapping("/clear")
    public String clearCache(){
        rolePermissionService.clearCache();
        return "OK";
    }
}
