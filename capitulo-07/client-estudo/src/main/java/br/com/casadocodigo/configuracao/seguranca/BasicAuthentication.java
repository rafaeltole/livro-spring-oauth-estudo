package br.com.casadocodigo.configuracao.seguranca;

import java.util.Base64;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BasicAuthentication {
    @Getter
    private final String login;

    @Getter
    private final String senha;

    public String getCredenciaisBase64() {
        final String credenciais = login + ":" + senha;
        final String credenciaisCodificadasComBase64 = new String(Base64.getEncoder().encode(credenciais.getBytes()));

        return credenciaisCodificadasComBase64;
    }

}
