package com.x264.webmtotelegram.JPA;

import com.x264.webmtotelegram.ImageBoard.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaFile,String> {
}
