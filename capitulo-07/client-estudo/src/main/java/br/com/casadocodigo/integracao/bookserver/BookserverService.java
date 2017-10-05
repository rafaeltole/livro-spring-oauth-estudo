package br.com.casadocodigo.integracao.bookserver;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.casadocodigo.integracao.model.Livro;

@Service
public class BookserverService {

    public List<Livro> livros(final String token) throws UsuarioSemAutorizacaoException {
        final RestTemplate restTemplate = new RestTemplate();

        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);

        final String endpoint = "http://localhost:8080/api/v2/livros";

        final RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(endpoint));

        try {
            final ResponseEntity<Livro[]> resposta = restTemplate.exchange(request, Livro[].class);
            if (resposta.getStatusCode().is2xxSuccessful()) {
                return listaFromArray(resposta.getBody());
            } else {
                throw new RuntimeException("sem sucesso");
            }
        } catch (final HttpClientErrorException e) {
            throw new UsuarioSemAutorizacaoException("não foi possível obter os livros do usuário");
        }

    }

    private List<Livro> listaFromArray(final Livro[] livros) {
        final List<Livro> lista = new ArrayList<>();

        for (final Livro livro : livros) {
            lista.add(livro);
        }

        return lista;
    }

}
