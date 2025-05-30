package com.javabd.jogo.service;

import com.javabd.jogo.model.Usuario;
import com.javabd.jogo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario atualizar(Integer id, Usuario novoUsuario) {
        return repository.findById(id).map(usuario -> {
            usuario.setNome(novoUsuario.getNome());
            usuario.setPontuacao(novoUsuario.getPontuacao());
            usuario.setInimigos(novoUsuario.getInimigos());
            return repository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }

    public List<Usuario> top10Pontuacao() {
        return repository.findTop10ByOrderByPontuacaoDesc();
    }

    public Usuario cadastrar(Usuario usuario) {
        if (repository.findByNome(usuario.getNome()).isPresent()) {
            throw new RuntimeException("Nome de usuário já está em uso");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repository.save(usuario);
    }

    public Optional<Usuario> logar(String nome, String senha) {
        Optional<Usuario> usuario = repository.findByNome(nome);
        if (usuario.isPresent()) {
            boolean senhaCorreta = passwordEncoder.matches(senha, usuario.get().getSenha());
            if (senhaCorreta) {
                return usuario;
            }
        }
        return Optional.empty();
    }

    public Usuario atualizarInimigos(Integer id, Long novosInimigos) {
        return repository.findById(id).map(usuario -> {
            usuario.setInimigos(novosInimigos);
            return repository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    public Usuario atualizarPontuacao(Integer id, Long novaPontuacao) {
        return repository.findById(id).map(usuario -> {
            usuario.setPontuacao(novaPontuacao);
            return repository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }
}
