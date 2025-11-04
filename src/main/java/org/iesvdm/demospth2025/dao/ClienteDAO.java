package org.iesvdm.demospth2025.dao;

import org.iesvdm.demospth2025.modelo.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO {

    void create(Cliente cliente);

    List<Cliente> getAll();

    Optional<Cliente>  find(int id);

    void update(Cliente cliente);

    void delete(int id);

}
