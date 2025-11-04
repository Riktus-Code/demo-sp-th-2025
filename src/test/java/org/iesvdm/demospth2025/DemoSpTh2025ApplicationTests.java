package org.iesvdm.demospth2025;

import org.iesvdm.demospth2025.dao.ClienteDAO;
import org.iesvdm.demospth2025.dao.ClienteDAOImpl;
import org.iesvdm.demospth2025.modelo.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class DemoSpTh2025ApplicationTests {

    //Spring es un framework de Inyección de Dependencias e Inversión de Control.

    @Autowired
    ClienteDAO clienteDAO;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAll() {
        List<Cliente> list = clienteDAO.getAll();

        list.forEach(System.out::println);
    }

    @Test
    void testFind(){
        Optional<Cliente> optionalCliente = clienteDAO.find(3);

        if(optionalCliente.isPresent()){
            System.out.println(optionalCliente.get());
        }else{
            System.out.println("Vacío!!!");
        }
    }

    @Test
    void testCreate(){
      Cliente cliente = Cliente.builder().nombre("Iván").apellido1("Alarcón").apellido2("Herrera").ciudad("Málaga").categoria(1)
                .build();

        System.out.println("Antes de crear id :" +cliente.getId());

        clienteDAO.create(cliente);

        System.out.println("Después de crear id: "+cliente.getId());
    }

    @Test
    void testUpdate(){

        Cliente cliente = Cliente.builder().nombre("Iván")
                .apellido1("Alarcón")
                .apellido2("Herrera")
                .ciudad("Málaga")
                .categoria(1)
                .build();
        clienteDAO.create(cliente);

        cliente.setNombre("Ivanito");

        clienteDAO.update(cliente);

        Optional<Cliente> optionalClienteReal = clienteDAO.find(cliente.getId());

        if(optionalClienteReal.isPresent()){
            Assertions.assertEquals("Ivanito",optionalClienteReal.get().getNombre());
        }else{
            Assertions.fail();
        }
    }
    @Test
    void testDelete(){

        Cliente cliente = Cliente.builder().nombre("Iván")
                .apellido1("Alarcón")
                .apellido2("Herrera")
                .ciudad("Málaga")
                .categoria(1)
                .build();
        clienteDAO.create(cliente);

        clienteDAO.delete(cliente.getId());

        Optional<Cliente> optionalClienteReal = clienteDAO.find(cliente.getId());

            Assertions.assertTrue(optionalClienteReal.isEmpty());

    }
}
