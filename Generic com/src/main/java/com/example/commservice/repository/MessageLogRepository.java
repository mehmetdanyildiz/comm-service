package com.example.commservice.repository;

import com.example.commservice.model.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageLogRepository extends JpaRepository<MessageLog, UUID> { }
