package com.x264.webmtotelegram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

import com.x264.webmtotelegram.entities.ImageBoardThread;

public interface ThreadRepository extends JpaRepository<ImageBoardThread,Long> {
    ArrayList<ImageBoardThread> findAll();
    @Query("select i.idThread from ImageBoardThread i")
    ArrayList<Long> findAllByIdThread();

}
