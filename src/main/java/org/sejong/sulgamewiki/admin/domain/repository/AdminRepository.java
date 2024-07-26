package org.sejong.sulgamewiki.admin.domain.repository;

import org.sejong.sulgamewiki.admin.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

}