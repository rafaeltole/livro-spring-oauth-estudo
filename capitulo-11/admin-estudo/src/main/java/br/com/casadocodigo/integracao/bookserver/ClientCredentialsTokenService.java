package br.com.casadocodigo.integracao.bookserver;

import java.net.URI;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.casadocodigo.integracao.BasicAuthentication;
import br.com.casadocodigo.integracao.OAuth2Token;

@Service
public class ClientCredentialsTokenService {

    public OAuth2Token getToken() {

        final RestTemplate restTemplate = new RestTemplate();
        final BasicAuthentication clientAuthentication = new BasicAuthentication("cliente-admin", "123abc");

        final RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(getBody(), getHeader(clientAuthentication), HttpMethod.POST, URI.create("http://localhost:8080/oauth/token"));

        final ResponseEntity<OAuth2Token> responseEntity = restTemplate.exchange(requestEntity, OAuth2Token.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        // isso deve ser tratado de forma melhor (apenas para exemplo)
        throw new RuntimeException("error trying to retrieve access token");
    }

    private MultiValueMap<String, String> getBody() {
        final MultiValueMap<String, String> dadosFormulario = new LinkedMultiValueMap<>();
        dadosFormulario.add("grant_type", "client_credentials");
        return dadosFormulario;
    }

    private HttpHeaders getHeader(final BasicAuthentication clientAuthentication) {
        final HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Basic " + clientAuthentication.getCredenciaisBase64());

        return httpHeaders;
    }
}
