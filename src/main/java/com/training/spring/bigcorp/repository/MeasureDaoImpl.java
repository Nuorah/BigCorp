package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Measure;
import com.training.spring.bigcorp.model.Site;
import com.training.spring.bigcorp.utils.H2DateConverter;
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
public class MeasureDaoImpl implements MeasureDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private H2DateConverter h2DateConverter;

    private static String SELECT_WITH_JOIN =
            "SELECT m.id, m.instant, m.value_in_watt, m.captor_id," +
                    " c.name as captor_name, c.site_id, s.name as site_name" +
                    " FROM Measure m inner join Captor c on m.captor_id=c.id" +
                    " inner join site s on c.site_id = s.id ";

    private Measure measureMapper(ResultSet rs, int rowNum) throws SQLException {
        Site site = new Site(rs.getString("site_name"));
        site.setId(rs.getString("site_id"));
        Captor captor = new Captor(rs.getString("captor_name"), site);
        captor.setId(rs.getString("captor_id"));
        Measure measure = new Measure(h2DateConverter.convert(rs.getString("instant")),
                rs.getInt("value_in_watt"),
                captor);
        measure.setId(rs.getLong("id"));
        return measure;
    }

    @Override
    public void create(Measure measure) {
        jdbcTemplate.update("INSERT INTO MEASURE (INSTANT, VALUE_IN_WATT, CAPTOR_ID)" +
                        " VALUES (:instant, :valueInWatts, :captor_id)",
                new MapSqlParameterSource()
                        .addValue("instant", measure.getInstant())
                        .addValue("valueInWatts", measure.getValueInWatt())
                        .addValue("captor_id", measure.getCaptor().getId())
        );
    }

    @Override
    public Measure findById(String id) {
        try {
            Measure foundMeasure = jdbcTemplate.queryForObject(SELECT_WITH_JOIN + " Where m.id = :id",
                    new MapSqlParameterSource()
                            .addValue("id", id),
                    this::measureMapper);
            foundMeasure.setId(Long.parseLong(id));
            return foundMeasure;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Measure> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOIN, this::measureMapper);
    }

    @Override
    public void update(Measure measure) {
        jdbcTemplate.update("update MEASURE set instant = :instant," +
                        " value_in_watt = :valueInWatt," +
                        " captor_id = :captor_id " +
                        "where id =:id",
                new MapSqlParameterSource()
                        .addValue("id", measure.getId())
                        .addValue("instant", measure.getInstant())
                        .addValue("valueInWatt", measure.getValueInWatt())
                        .addValue("captor_id", measure.getCaptor().getId()));
    }

    @Override
    public void deleteById(String id) {
        jdbcTemplate.update("Delete FROM MEASURE WHERE id = :id",
                new MapSqlParameterSource()
                        .addValue("id", id));
    }
}
