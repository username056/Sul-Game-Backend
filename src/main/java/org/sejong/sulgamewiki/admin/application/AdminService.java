package org.sejong.sulgamewiki.admin.application;

import lombok.RequiredArgsConstructor;
import org.sejong.sulgamewiki.admin.domain.entity.Admin;
import org.sejong.sulgamewiki.admin.domain.repository.AdminRepository;
import org.sejong.sulgamewiki.admin.dto.AdminDto;
import org.sejong.sulgamewiki.admin.exception.AdminErrorCode;
import org.sejong.sulgamewiki.admin.exception.AdminException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminDto createAdmin(Admin admin) {
        Admin savedAdmin = adminRepository.save(admin);
        return AdminDto.from(savedAdmin);
    }

    public AdminDto getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ADMIN_NOT_FOUND));
        return AdminDto.from(admin);
    }

    public AdminDto updateAdmin(Long id, Admin adminDetails) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ADMIN_NOT_FOUND));
        admin.setNickname(adminDetails.getNickname());
        admin.setEmail(adminDetails.getEmail());

        Admin updatedAdmin = adminRepository.save(admin);
        return AdminDto.from(updatedAdmin);
    }

    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ADMIN_NOT_FOUND));
        adminRepository.delete(admin);
    }

}