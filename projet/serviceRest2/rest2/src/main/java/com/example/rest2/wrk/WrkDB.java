// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//  */
// package workers;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// /**
//  *
//  * @author BuetschiD
//  */
// public class WrkDB {
//     private String port;
//     private String dbName;
    
//     public WrkDB(String port, String dbName){
//         this.port = port;
//         this.dbName = dbName;
//     }
    
//      /**
//      * Ouverture d'une connexion sur la base de données.
//      * Ajouter mysql-connector-j-....jar dans les librairies.
//      * 
//      * @return true si tout s'est bien passé, sinon false
//      */
//      private Connection dbConnexion;
    
//     public boolean openConnexion(){

//         final String url = "jdbc:mysql://localhost:" + port + "/" + dbName + "?serverTimezone=CET";
//         final String user = "root";
//         final String pw =  "";// 
//         boolean result = false;
 
//         try{
//             //nécessaire pour fonctionnement en web
//             Class.forName("com.mysql.jdbc.Driver");
//         } catch ( ClassNotFoundException ex ) {
//              System.out.println("Connexion au driver JDBC à échoué!\n" + ex.getMessage());
//         } 
//         try {
//             dbConnexion = DriverManager.getConnection( url, user, pw );
//             //System.out.println("Connection successfull");
//             result = true;

//         } catch ( SQLException ex ) {
//              System.out.println("Connexion à la BD a échouée!\n" + ex.getMessage());
//         }     
//         return result;
//     }
    
//      /**
//      * Cette méthode permet de fermer la connexion à la base de données après
//      * que la requête ait été envoyée.
//      * @return true si tout s'est bien passé, sinon false
//      */
//     public boolean closeConnexion() {
//         boolean result = false;
//         try {
//             if (dbConnexion != null) {
//                 if (!dbConnexion.isClosed()) {
//                     dbConnexion.close();
//                 } else {
//                 }
//             } else {
//             }
//             result = true;
//         } catch (SQLException e) {
//         }
//         return result;
//     }

//     public ArrayList<String> getPays() {
//         ArrayList<String> lstPays = null;
//         boolean result = openConnexion();
//         if (result) {
//             System.out.println("connection ok");
//             PreparedStatement ps = null;
//             String pays = "";
//             lstPays = new ArrayList<String>();
//             try {
//                 ps = dbConnexion.prepareStatement("SELECT Nom FROM t_pays");
//                 ResultSet rs = ps.executeQuery();
//                 while (rs.next()) {
//                     pays = (String) rs.getString(1);
//                     lstPays.add(pays);
//                 }
//                 rs.close();
//                 result = true;
//                 System.out.println("OK");
//             } catch (Exception ex) {
//                 System.out.println(ex.getMessage());
//             }

//             if (result) {
//                 result = closeConnexion();
//             }
//         }
//         return lstPays;
//     }
// }
