package br.com.casadocodigo.usuarios;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@ToString
public class AcessoBookserver {

    @Getter
    @Setter
    @Column(name = "token_bookserver")
    private String accessToken;

}
