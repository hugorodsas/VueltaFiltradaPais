import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url="jdbc:oracle:thin:@localhost:1521:xe";
        String user="RIBERA";
        String password="ribera";
        Scanner sc=new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url,user,password);
             Statement statement = conn.createStatement()){

            System.out.println("¿De que nacionalidad?");
            String nacional=sc.nextLine();
            String sql = "SELECT C.ID_CICLISTA AS IDCICLISTA, C.NOMBRE, C.NACIONALIDAD, C.EDAD, E.NOMBRE AS EQUIPO " +
                    "FROM CICLISTA C " +
                    "JOIN EQUIPO E ON C.ID_EQUIPO = E.ID_EQUIPO "+
                    "WHERE C.NACIONALIDAD = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, nacional);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("IDCICLISTA");
                        String cilista = rs.getString("NOMBRE");
                        String nacionalidad = rs.getString("NACIONALIDAD");
                        int edad = rs.getInt("EDAD");
                        String equipo = rs.getString("EQUIPO");

                        System.out.println("ID: "+id+", Nombre: "+cilista+", Nacionalidad: "+nacionalidad+", Edad: "+edad+", Equipo: "+equipo);
                    }
                }
            }
        }catch (SQLException e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }
}
