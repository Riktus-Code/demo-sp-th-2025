package org.iesvdm.demospth2025.controller;

import org.iesvdm.demospth2025.modelo.Cliente;
import org.iesvdm.demospth2025.servicio.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //ENDPOINT para la ruta http://localhost:8080/clientes
    @GetMapping("/clientes")
    public String listarClientes(Model model){  //Importamos el model del framework (en este caso SPRING)

        List<Cliente> clienteList =clienteService.all();

        model.addAttribute("listaClientes",clienteList);

        return "clientes";
    }
}

