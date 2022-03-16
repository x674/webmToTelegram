package com.x264.webmtotelegram.Repositories;

import com.x264.webmtotelegram.Entities.MediaFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaFile,String> {
}
