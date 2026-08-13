package cr.ac.ucenfotec.dl;

import java.sql.*;
import java.time.LocalDate;

public class DBAccess {

    // Atributos para manejar la conexión y las consultas a la base de datos
    private Connection connection;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    // Constructor
    public DBAccess(String direccion, String usuario, String contrasenia)
            throws ClassNotFoundException, SQLException {

        // Se carga el driver de MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Se establece la conexión con la base de datos
        connection = DriverManager.getConnection(
                direccion,
                usuario,
                contrasenia
        );
    }

    // Ejecuta INSERT, UPDATE o DELETE sin parámetros
    public void ejecutarStatement(String pStatement)
            throws SQLException {

        statement = connection.createStatement();

        statement.executeUpdate(pStatement);
    }

    // Ejecuta un statement con un parámetro entero
    public void ejecutarStatement(String pStatement, int pValor1)
            throws Exception {

        preparedStatement =
                connection.prepareStatement(pStatement);

        preparedStatement.setInt(1, pValor1);

        preparedStatement.executeUpdate();
    }

    // Ejecuta un statement con dos parámetros enteros
    public void ejecutarStatement(String pStatement,
                                  int pValor1,
                                  int pValor2)
            throws Exception {

        preparedStatement =
                connection.prepareStatement(pStatement);

        preparedStatement.setInt(1, pValor1);
        preparedStatement.setInt(2, pValor2);

        preparedStatement.executeUpdate();
    }

    // Ejecuta un statement con un parámetro String
    public void ejecutarStatement(String pStatement,
                                  String pValor1)
            throws Exception {

        preparedStatement =
                connection.prepareStatement(pStatement);

        preparedStatement.setString(1, pValor1);

        preparedStatement.executeUpdate();
    }

    // Ejecuta un statement con dos parámetros String
    public void ejecutarStatement(String pStatement,
                                  String pValor1,
                                  String pValor2)
            throws Exception {

        preparedStatement =
                connection.prepareStatement(pStatement);

        preparedStatement.setString(1, pValor1);
        preparedStatement.setString(2, pValor2);

        preparedStatement.executeUpdate();
    }

    // Ejecuta un statement con tres parámetros String
    public void ejecutarStatement(String pStatement,
                                  String pValor1,
                                  String pValor2,
                                  String pValor3)
            throws Exception {

        preparedStatement =
                connection.prepareStatement(pStatement);

        preparedStatement.setString(1, pValor1);
        preparedStatement.setString(2, pValor2);
        preparedStatement.setString(3, pValor3);

        preparedStatement.executeUpdate();
    }

    // Ejecuta un statement con un parámetro float
    public void ejecutarStatement(String pStatement,
                                  float pValor1)
            throws Exception {

        preparedStatement =
                connection.prepareStatement(pStatement);

        preparedStatement.setFloat(1, pValor1);

        preparedStatement.executeUpdate();
    }

    // Ejecuta un statement con un parámetro LocalDate
    public void ejecutarStatement(String pStatement,
                                  LocalDate pValor1)
            throws Exception {

        preparedStatement =
                connection.prepareStatement(pStatement);

        preparedStatement.setDate(
                1,
                Date.valueOf(pValor1)
        );

        preparedStatement.executeUpdate();
    }

    // Ejecuta consultas SELECT sin parámetros
    public ResultSet ejecutarQuery(String pQuery)
            throws SQLException {

        statement = connection.createStatement();

        return statement.executeQuery(pQuery);
    }

    // Ejecuta consultas SELECT con un parámetro entero
    public ResultSet ejecutarQuery(String pQuery,
                                   int pValor)
            throws SQLException {

        preparedStatement =
                connection.prepareStatement(pQuery);

        preparedStatement.setInt(1, pValor);

        return preparedStatement.executeQuery();
    }

    // Ejecuta consultas SELECT con un parámetro String
    public ResultSet ejecutarQuery(String pQuery,
                                   String pValor)
            throws SQLException {

        preparedStatement =
                connection.prepareStatement(pQuery);

        preparedStatement.setString(1, pValor);

        return preparedStatement.executeQuery();
    }

    // Ejecuta consultas SELECT con dos parámetros enteros
    public ResultSet ejecutarQuery(String pQuery,
                                   int pValor1,
                                   int pValor2)
            throws SQLException {

        preparedStatement =
                connection.prepareStatement(pQuery);

        preparedStatement.setInt(1, pValor1);
        preparedStatement.setInt(2, pValor2);

        return preparedStatement.executeQuery();
    }
}
