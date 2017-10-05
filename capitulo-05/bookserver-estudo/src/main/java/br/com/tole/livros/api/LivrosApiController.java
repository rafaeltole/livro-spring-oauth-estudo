package br.com.tole.livros.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.tole.configuracao.seguranca.ResourceOwner;
import br.com.tole.livros.Estante;
import br.com.tole.usuarios.Usuario;
import br.com.tole.usuarios.Usuarios;

@RestController
@RequestMapping("/api/livros")
public class LivrosApiController {

    @Autowired
    private Usuarios usuarios;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> livros() {

        Estante estante = donoDosLivros().getEstante();

        if (estante.temLivros()) {
            return new ResponseEntity<>(estante.todosLivros(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    private Usuario donoDosLivros() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ResourceOwner donoDosLivros = (ResourceOwner) authentication.getPrincipal();

        return usuarios.buscarPorID(donoDosLivros.getId());
    }
}
