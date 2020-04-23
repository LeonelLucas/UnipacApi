package com.example.apiTrabalhoUnipac.model.repository;


import com.example.apiTrabalhoUnipac.model.domain.Trabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabalhoRepositorio extends JpaRepository<Trabalho,Long> {
}
