package com.javabd.jogo.controller;

import com.javabd.jogo.model.Usuario;
import com.javabd.jogo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // GET /usuarios
    @GetMapping
    public List<Usuario> listarTodos() {
        return service.listarTodos();
    }

    // GET /usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = service.buscarPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /usuarios
    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.salvar(usuario));
    }

    // PUT /usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(service.atualizar(id, usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // GET /usuarios/top10
    @GetMapping("/top10")
    public List<Usuario> top10Pontuacao() {
        return service.top10Pontuacao();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = service.cadastrar(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario loginRequest) {
        Optional<Usuario> usuario = service.logar(loginRequest.getNome(), loginRequest.getSenha());
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    // PATCH /usuarios/{id}/inimigos
    @PatchMapping("/{id}/inimigos")
    public ResponseEntity<Usuario> atualizarInimigos(@PathVariable Integer id, @RequestBody Long novosInimigos) {
        try {
            Usuario usuarioAtualizado = service.atualizarInimigos(id, novosInimigos);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /usuarios/{id}/pontuacao
    @PatchMapping("/{id}/pontuacao")
    public ResponseEntity<Usuario> atualizarPontuacao(@PathVariable Integer id, @RequestBody Long novaPontuacao) {
        try {
            Usuario usuarioAtualizado = service.atualizarPontuacao(id, novaPontuacao);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
