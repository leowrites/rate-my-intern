package service.dao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Internship;

import entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InternshipDaoMapper implements RowMapper<Internship> {

    @Override
    public Internship mapRow(ResultSet rs, int rowNum) throws SQLException {
        String internshipData = rs.getString("data");
        Gson gson = new Gson();
        JsonObject object = (JsonObject) JsonParser.parseString(internshipData);
        System.out.println(object);
        User user = gson.fromJson(object, User.class);
        System.out.println(user);
        Internship internship = gson.fromJson(object, Internship.class);
        internship.setCompanyID(rs.getInt("companyid"));
        return internship;
    }
}