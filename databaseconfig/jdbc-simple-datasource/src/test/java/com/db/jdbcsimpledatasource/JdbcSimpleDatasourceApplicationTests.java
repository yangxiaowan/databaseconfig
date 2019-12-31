package com.db.jdbcsimpledatasource;

import com.db.jdbcsimpledatasource.config.MyDataSource;
import com.db.jdbcsimpledatasource.entity.Student;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcSimpleDatasourceApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void springDataSourceTest() {
        //输出为true
        System.out.println(dataSource instanceof HikariDataSource);
        System.out.println(dataSource instanceof MyDataSource);
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
