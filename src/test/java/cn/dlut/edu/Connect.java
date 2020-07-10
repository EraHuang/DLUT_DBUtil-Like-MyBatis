package cn.dlut.edu;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import cn.dlut.edu.connect.ConnectorProvider;

public class Connect {
    public static void main(String[] args) throws Exception {
        InputStream resourceAsStream = Connect.class.getClassLoader().getResourceAsStream("jdbc.property");
        ConnectorProvider connectorProvider = new ConnectorProvider(resourceAsStream);
        Connection connection = connectorProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from student");
        preparedStatement.executeQuery();
    }
}
