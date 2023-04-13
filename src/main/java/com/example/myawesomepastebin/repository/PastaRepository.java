package com.example.myawesomepastebin.repository;

import com.example.myawesomepastebin.model.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PastaRepository extends JpaRepository<Pasta, String> {
}
