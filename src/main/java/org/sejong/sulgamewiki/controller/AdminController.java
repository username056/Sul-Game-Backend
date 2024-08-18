package org.sejong.sulgamewiki.controller;

import lombok.RequiredArgsConstructor;

import org.sejong.sulgamewiki.service.AdminService;
import org.sejong.sulgamewiki.admin.dto.AdminDto;
import org.sejong.sulgamewiki.util.log.LogMonitoringInvocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/{id}")
    @LogMonitoringInvocation
    public AdminDto getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

}