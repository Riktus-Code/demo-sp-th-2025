package org.iesvdm.demospth2025.dao;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.demospth2025.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
//Un Repository es un componente y a su vez un estereotipo de Spring
//que forma parte de la 'capa de persistencia'.
@Repository

public class ClienteDAOImpl implements ClienteDAO {


    private final JdbcClient jdbcClient;
    private final JdbcTemplate jdbcTemplate;

    public ClienteDAOImpl(JdbcClient jdbcClient, JdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Cliente cliente) {
        String sql = """
                insert into cliente (nombre, apellido1, apellido2, ciudad, categoría)
                values (      ?,      ?,     ?,     ?,      ?);
                """;
                String[] ids = {"id"};
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(con->{

                    PreparedStatement ps = con.prepareStatement(sql,ids);

                    ps.setString(1,cliente.getNombre());
                    ps.setString(2,cliente.getApellido1());
                    ps.setString(3,cliente.getApellido2());
                    ps.setString(4,cliente.getCiudad());
                    ps.setInt(5,cliente.getCategoria());


                    return ps;
                }
                ,keyHolder);
                cliente.setId((int)keyHolder.getKey().intValue());

    }

    @Override
    public List<Cliente> getAll() {
        List<Cliente> listClientes = jdbcTemplate.query("""
                Select * 
                from cliente
                """,
                (rs, rowNum) -> new Cliente(
                                        rs.getInt("id"),
                                        rs.getString("nombre"),
                                        rs.getString("apellido1"),
                                        rs.getString("apellido2"),
                                        rs.getString("ciudad"),
                                        rs.getInt("categoría")
                                    )
                );

        log.info("Devueltos {} clientes",listClientes.size());
        log.debug("Devueltos {} clientes ",listClientes.size());

        return listClientes;
    }

    @Override
    public Optional<Cliente> find(int id) {
        try {
            Cliente cliente = jdbcTemplate.queryForObject("""
                        
                        SELECT *
                        FROM cliente 
                        WHERE id = ?
                        """,
                        (rs,rowNum)->Cliente.builder()
                                .id(rs.getInt("id"))
                                .nombre(rs.getString("nombre"))
                                .apellido1(rs.getString("apellido1"))
                                .apellido2(rs.getString("apellido2"))
                                .ciudad(rs.getString("ciudad"))
                                .categoria(rs.getInt("categoría"))
                                .build()
                        ,
                        id

        );
            return Optional.of(cliente);

    } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void update(Cliente cliente) {
       var rowsUpdate = jdbcTemplate.update("""
                UPDATE cliente
                SET nombre = ?,apellido1 = ?,apellido2 = ?, ciudad = ?, categoría = ?
                WHERE id = ?
                """, cliente.getNombre(),
                cliente.getApellido1(),
                cliente.getApellido2(),
                cliente.getCiudad(),
                cliente.getCategoria(),
                cliente.getId()
        );

        log.debug("Flias actualizadas {}", rowsUpdate);

    }

    @Override
    public void delete(int id) {

        int rowsUpdate = jdbcTemplate.update("""
        DELETE FROM cliente
        WHERE id = ?
        """
        ,id);
        log.debug("Flias actualizadas {}", rowsUpdate);

        }

    }

