package org.iesvdm.demospth2025.controller;


import jakarta.servlet.http.HttpSession;
import org.iesvdm.demospth2025.modelo.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DemothController {

    //SIN SERVICE, SOLO ACTUAR SOBRE PLANTILLAS HTML

    //ENDPOINTS

    @GetMapping("/demoth1")
    public String demothText(Model model){

        model.addAttribute("parrafo2", "En un lugar de la Mancha...");

        return "plantilla1";
    }

    @GetMapping ("/demoth2")
    public String demothBlock (Model model){

        Cliente cliente1 = Cliente.builder().nombre("Don Miguel de Cervantes").build();

        Cliente cliente2 = Cliente.builder().nombre("Lope de Vega").build();

        List<Cliente> list = List.of(cliente1, cliente2);

        model.addAttribute("escritores", list);

        return "plantilla2";
    }

    @GetMapping ("/demoth3")
    public String demothHttpSession1(Model model, HttpSession session){

        String mensajeASesion = "Lo grabé a demoth3";

        String mensajeAModel = "Esto sólo lo ve la plantilla demoth3";

        //atributos en model solo visibles en esta plantilla
        model.addAttribute("msgModel", mensajeAModel);
        //atributo en sesión son visibles en todos los endpoints/plantillas
        session.setAttribute("msgSession", mensajeASesion);

        return "demoth3";
    }

    @GetMapping ("/demoth3_2")
    public String demothHttpSession2(Model model, HttpSession session){

        return "demoth3_2";
    }


    @GetMapping("/demoth5")
    public String demoth5(Model model){
        Cliente cliente = Cliente.builder()
                .id(101)
                .nombre("Cervantes")
                .build();

        model.addAttribute("cliente",cliente);

        return "plantilla5";
    }
}