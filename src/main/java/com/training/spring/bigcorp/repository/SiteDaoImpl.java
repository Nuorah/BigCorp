package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class SiteDaoImpl implements SiteDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private Site siteMapper(ResultSet rs, int rowNum) throws SQLException {
        Site site = new Site(rs.getString("name"));
        site.setId(rs.getString("id"));
        return site;
    }

    @Override
    public void create(Site site) {
        jdbcTemplate.update("INSERT INTO SITE VALUES(:id, :name)",
                new MapSqlParameterSource()
                        .addValue("id", site.getId())
                        .addValue("name", site.getName())
        );
    }

    @Override
    public Site findById(String id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM SITE WHERE id = :id",
                    new MapSqlParameterSource()
                            .addValue("id", id),
                    this::siteMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Site> findAll() {
        return jdbcTemplate.query("SELECT * FROM SITE", this::siteMapper);
    }

    @Override
    public void update(Site site) {
        jdbcTemplate.update("update SITE set name = :name where id =:id",
                new MapSqlParameterSource()
                        .addValue("id", site.getId())
                        .addValue("name", site.getName()));
    }

    @Override
    public void deleteById(String id) {
        jdbcTemplate.update("Delete FROM SITE WHERE id = :id",
                new MapSqlParameterSource()
                        .addValue("id", id));
    }


}
