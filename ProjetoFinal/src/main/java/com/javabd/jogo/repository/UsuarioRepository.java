package com.javabd.jogo.repository;

import com.javabd.jogo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findTop10ByOrderByPontuacaoDesc();
    Optional<Usuario> findByNome(String nome);
}
