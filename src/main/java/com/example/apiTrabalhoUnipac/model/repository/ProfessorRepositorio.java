package com.example.apiTrabalhoUnipac.model.repository;


import com.example.apiTrabalhoUnipac.model.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepositorio extends JpaRepository<Professor, Long> {
}
