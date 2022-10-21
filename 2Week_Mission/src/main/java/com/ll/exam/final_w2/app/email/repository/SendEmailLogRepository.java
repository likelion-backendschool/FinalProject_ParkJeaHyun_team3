package com.ll.exam.final_w2.app.email.repository;

import com.ll.exam.final_w2.app.email.entity.SendEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendEmailLogRepository extends JpaRepository<SendEmailLog, Long> {
}
