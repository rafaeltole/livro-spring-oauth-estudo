package br.com.tole.livros.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.tole.livros.RepositorioDeLivros;

@RestController
@RequestMapping("/api/v2/admin")
public class AdministracaoController {

    @Autowired
    private RepositorioDeLivros repositorioDeLivros;

    @RequestMapping(method = RequestMethod.GET, value = "/total_livros")
    public ResponseEntity<Long> getTotalDeLivros() {
        final Long totalDeLivros = repositorioDeLivros.getTotalDeLivros();
        return new ResponseEntity<>(totalDeLivros, HttpStatus.OK);
    }

}
