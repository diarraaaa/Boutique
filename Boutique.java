import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
public class Boutique{
    // JDBC URL, username and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/produit";
    private static final String USER = "diarra";
    private static final String PASSWORD = "passer";
    public static void main(String[] args) {
        Connection connection = null;
        Scanner  typin= new Scanner(System.in);
        try {
            // Establishing connection to MySQL database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if(connection==null){
                System.out.println("Connection échouée");

            }else{
                System.out.println("Connection réuissie");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
            System.out.println("QUEL ACTION EXECUTER: ");
            System.out.println("1---Ajout de produit-- ");
            System.out.println("2---Affichage des produits-- ");
            System.out.println("3---Vente de produits-- ");
            System.out.println("4---Ajouter du stock-- ");
            System.out.println("5---Changer un prix-- ");
            int choix=typin.nextInt();
            typin.nextLine();
            if (choix==1) {
                System.out.println("Veuillez saisir le nom:  ");
                String nom=typin.nextLine();
                System.out.println("Veuillez saisir le prix:  ");
                int prix=typin.nextInt();
                System.out.println("Veuillez saisir la quantité:  ");
                int quantité=typin.nextInt();
                Gestionbase.ajoutproduit(connection,nom,prix,quantité); 
            }else if(choix==2){
                System.out.println("Voici vos données");
                Gestionbase.listeproduit(connection);
            }else if(choix==3){
                Gestionbase.venteproduit(connection);
            }else if (choix==4) 
            {
                Gestionbase.restockproduit(connection);  
            }else if(choix==5){
                Gestionbase.changerprix(connection);
            }else{
                System.out.println("Votre choix est invalide");
            }
    }
}
