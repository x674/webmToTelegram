package com.x264.webmtotelegram.repositories;

import com.x264.webmtotelegram.entities.MediaFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaFile,String> {
}
