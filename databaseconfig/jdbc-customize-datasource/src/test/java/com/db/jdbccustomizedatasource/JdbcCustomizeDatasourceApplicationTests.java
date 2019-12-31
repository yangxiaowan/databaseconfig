package com.db.jdbccustomizedatasource;

import com.db.jdbccustomizedatasource.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcCustomizeDatasourceApplicationTests {

    @Autowired
    @Qualifier("myDbcp2DataSource")
    private DataSource dataSource;

    @Test
    public void springJdbcTemplateTest() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String queryStr = "select * from student";
            List<Student> resultList = new ArrayList<>();
            jdbcTemplate.query(queryStr, (ResultSet resultSet) -> {
                Student student = new Student();
                student.setId(resultSet.getString("id"));
                student.setStudentId(resultSet.getString("student_id"));
                student.setStudentName(resultSet.getString("student_name"));
                student.setAge(resultSet.getInt("age"));
                resultList.add(student);
            });
            resultList.forEach((Student student) -> System.out.println(student));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void springDataSourceTest() {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from student");
            Student student = null;
            if (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getString("id"));
                student.setStudentId(resultSet.getString("student_id"));
                student.setStudentName(resultSet.getString("student_name"));
                student.setAge(resultSet.getInt("age"));
            }
            System.out.println(student);
            statement.close();
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
