package br.com.casadocodigo.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.integracao.bookserver.BookserverService;

@Controller
public class DashboardController {

    @Autowired
    private BookserverService bookserverService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        final ModelAndView mv = new ModelAndView("home");

        final long quantidadeDeLivros = bookserverService.getQuantidadeDeLivrosCadastrados();
        mv.addObject("quantidadeDeLivros", quantidadeDeLivros);

        return mv;
    }

}
