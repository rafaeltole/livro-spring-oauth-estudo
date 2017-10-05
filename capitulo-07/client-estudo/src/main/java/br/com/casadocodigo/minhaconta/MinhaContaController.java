package br.com.casadocodigo.minhaconta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.configuracao.seguranca.UsuarioLogado;
import br.com.casadocodigo.integracao.bookserver.BookserverService;
import br.com.casadocodigo.integracao.bookserver.UsuarioSemAutorizacaoException;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.UsuariosRepository;

@Controller
@RequestMapping("/minhaconta")
public class MinhaContaController {

    @Autowired
    private BookserverService bookserverService;

    @Autowired
    private UsuariosRepository usuarios;

    @RequestMapping(value = "/principal")
    public ModelAndView principal() {

        final Usuario usuario = usuarioLogado();
        final String token = usuario.getAcessoBookserver().getAccessToken();

        final ModelAndView mv = new ModelAndView("minhaconta/principal");

        try {
            mv.addObject("livros", bookserverService.livros(token));
        } catch (final UsuarioSemAutorizacaoException e) {
            mv.addObject("erro", e.getMessage());
        }

        return mv;

    }

    private Usuario usuarioLogado() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        return usuarios.findById(usuarioLogado.getId());
    }

}
