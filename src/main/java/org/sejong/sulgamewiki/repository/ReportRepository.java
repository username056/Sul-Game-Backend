package org.sejong.sulgamewiki.repository;

import java.util.List;
import org.sejong.sulgamewiki.object.Member;
import org.sejong.sulgamewiki.object.Report;
import org.sejong.sulgamewiki.object.constants.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
  boolean existsByReporterAndSourceIdAndSourceType(Member reporter, Long sourceId, SourceType sourceType);

  List<Report> findByReporter(Member member);

  void deleteByReporter(Member member);

  void deleteBySourceIdAndSourceType(Long sourceId, SourceType sourceType);
}
