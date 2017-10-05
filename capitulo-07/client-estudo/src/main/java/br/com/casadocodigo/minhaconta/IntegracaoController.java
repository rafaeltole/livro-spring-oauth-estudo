package br.com.casadocodigo.minhaconta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.configuracao.seguranca.UsuarioLogado;
import br.com.casadocodigo.integracao.bookserver.OAuth2Token;
import br.com.casadocodigo.integracao.bookserver.PasswordTokenService;
import br.com.casadocodigo.usuarios.AcessoBookserver;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.UsuariosRepository;

@Controller
@RequestMapping("/integracao")
public class IntegracaoController {

    @Autowired
    private PasswordTokenService passwordTokenService;

    @Autowired
    private UsuariosRepository usuarios;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView integracao() {
        return new ModelAndView("minhaconta/integracao");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView autorizar(final Autorizacao autorizacao) {
        final Usuario usuario = usuarioLogado();

        final OAuth2Token token = passwordTokenService.getToken(autorizacao.getLogin(), autorizacao.getSenha());

        final AcessoBookserver acessoBookserver = new AcessoBookserver();
        acessoBookserver.setAccessToken(token.getAccessToken());

        usuario.setAcessoBookserver(acessoBookserver);

        usuarios.save(usuario);

        return new ModelAndView("redirect:/minhaconta/principal");
    }

    private Usuario usuarioLogado() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        return usuarios.findById(usuarioLogado.getId());
    }

}
