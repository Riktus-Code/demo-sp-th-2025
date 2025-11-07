package org.iesvdm.demospth2025.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.demospth2025.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j

@Repository
public class ComercialDAOImpl implements ComercialDAO{

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ComercialDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void create(Comercial comercial) {
        String sql = """
                insert into comercial(nombre,apellido1,apellido2,comisión)
                values (      ?,      ?,     ?,     ?,      ?);
                """;
        String [] ids ={"id"};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con->{
            PreparedStatement ps = con.prepareStatement(sql,ids);
            ps.setString(1,comercial.getNombre());
            ps.setString(2, comercial.getApellido1());
            ps.setString(3,comercial.getApellido2());
            ps.setFloat(4,comercial.getComision());

            return ps;
        }
        ,keyHolder);
        comercial.setId((int)keyHolder.getKey().intValue());
    }

    @Override
    public List<Comercial> getAll() {
        List<Comercial>listComercial = jdbcTemplate.query("""
                select *
                from comercial
                """,
                (rs, rowNum) -> new Comercial(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getFloat("comisión")
                )
        );
        log.info("Devueltos{} comercial",listComercial.size());
        log.debug("Devueltos {} comercial",listComercial.size());
        return listComercial;
    }

    @Override
    public Optional<Comercial> find(int id) {
       try{
           Comercial comercial = jdbcTemplate.queryForObject("""
                   Select *
                   from comercial
                   where id=?
                   """,
                   (rs, rowNum) -> Comercial.builder()
                           .id(rs.getInt("id"))
                           .nombre(rs.getString("nombre"))
                           .apellido1(rs.getString("apellido1"))
                           .apellido2(rs.getString("apellido2"))
                           .comision(rs.getFloat("comisión"))
                           .build()
                   ,
                   id
           );
           return Optional.of(comercial);
       }catch (EmptyResultDataAccessException e){
           return Optional.empty();
       }
    }

    @Override
    public void update(Comercial comercial) {
    var rowsUpdate = jdbcTemplate.update("""
        UPDATE comercial
        set nombre=?,apellido1=?,apellido2=?,comision=?
        where id= ?
        """, comercial.getNombre(),
            comercial.getApellido1(),
            comercial.getApellido2(),
            comercial.getComision()
    );

    log.debug("Filas actualizadas{}",rowsUpdate);
    }

    @Override
    public void delete(int id) {
        int rowsUpdate = jdbcTemplate.update("""
        DELETE FROM comercial
        where id = ?
        """
        ,id);

        log.debug("Filas actualizadas{}",rowsUpdate);
    }
}
