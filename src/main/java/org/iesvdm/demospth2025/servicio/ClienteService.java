package org.iesvdm.demospth2025.servicio;

import org.iesvdm.demospth2025.dao.ClienteDAO;
import org.iesvdm.demospth2025.modelo.Cliente;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    //Implementar la lÃ³gica de negocio
    private ClienteDAO clienteDAO;

    //Inyeccion de dependencia de clienteDAO
    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public List<Cliente> all(){
        return clienteDAO.getAll();
    }
}