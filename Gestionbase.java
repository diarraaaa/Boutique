
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class Gestionbase {
    public static void ajoutproduit(Connection connection,String nom,int prix,int quantité){
        Statement statement =null;
        try{
         statement = connection.createStatement();
         String sql = "INSERT INTO inventaire (nom, prix, quantite) VALUES ('" + nom + "', " + prix + ", " + quantité + ")";
         int rowsAffected = statement.executeUpdate(sql);
         if(rowsAffected>0){
            System.out.println("Données ajoutées avec succes");
         }else{
            System.out.println("Echec d'ajout des données");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void listeproduit(Connection connection){
        Statement statement =null;
        ResultSet resultset=null;
        try{
         statement = connection.createStatement();
         String query = "SELECT * FROM inventaire";
         resultset=statement.executeQuery(query);
         while(resultset.next()){
             System.out.println("----ID:"+resultset.getInt("id")+" Nom:"+resultset.getString("nom")+" Prix:"+resultset.getInt("prix")+" Quantité:"+resultset.getInt("quantite")+"---");
             System.out.println("");
         }  
         resultset.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
			try {
				statement.close();
				connection.close();
			}catch (Exception e) {
                System.out.println(e.getMessage());
            }
		}
    }
    public static void venteproduit(Connection connection){
        Scanner scanner=new Scanner(System.in);
        Statement statement =null;
        ResultSet resultset=null;
        PreparedStatement preparedStatement=null;
        try{
         statement = connection.createStatement();
         System.out.println("Quel est le nom de l'article?: ");
         String nom=scanner.nextLine();
         System.out.println("Combien en vendez vous?: ");
         int restock=scanner.nextInt();
         String query = "SELECT quantite FROM inventaire WHERE nom ='"+nom+"'";
         resultset=statement.executeQuery(query);
         if (!resultset.next()) {
            System.out.println("L'article n'a pas été trouvé");
            return;
         }
         if (restock<=0)
          {
            System.out.println("Veuillez saisir une quantité valable");
            return;
         }
         int newquantite=resultset.getInt("quantite")-restock;
         String sql="UPDATE inventaire SET quantite =? WHERE nom=?";
         preparedStatement=connection.prepareStatement(sql);
         preparedStatement.setInt(1, newquantite);
         preparedStatement.setString(2, nom);
         preparedStatement.executeUpdate();
         resultset.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
			try {
				statement.close();
				preparedStatement.close();
                connection.close();
			}catch (Exception e) {
                System.out.println(e.getMessage());
            }
		}
    }
    public static void restockproduit(Connection connection){
        Scanner scanner=new Scanner(System.in);
        Statement statement =null;
        ResultSet resultset=null;
        PreparedStatement preparedStatement=null;
        try{
         statement = connection.createStatement();
         System.out.println("Quel est le nom de l'article?: ");
         String nom=scanner.nextLine();
         System.out.println("Combien en ajouter vous?: ");
         int restock=scanner.nextInt();
         String query = "SELECT quantite FROM inventaire WHERE nom ='"+nom+"'";
         resultset=statement.executeQuery(query);
         if (!resultset.next()) {
            System.out.println("L'article n'a pas été trouvé");
            return;
         }
         if (restock<=0)
          {
            System.out.println("Veuillez saisir une quantité valable");
            return;
         }
         int newquantite=resultset.getInt("quantite")+restock;
         if (newquantite>=0) {
         String sql="UPDATE inventaire SET quantite =? WHERE nom=?";
         preparedStatement=connection.prepareStatement(sql);
         preparedStatement.setInt(1, newquantite);
         preparedStatement.setString(2, nom);
         preparedStatement.executeUpdate();
         resultset.close();
         }else{
            System.out.println("La quantité est insuffisante!!");
         }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
			try {
				statement.close();
				preparedStatement.close();
                connection.close();
			}catch (Exception e) {
                System.out.println(e.getMessage());
            }
		}
    }
    public static void changerprix(Connection connection){
        Statement  statement =null;
        ResultSet resultset=null;
        PreparedStatement preparedStatement=null;
        Scanner scanner=new Scanner(System.in);
        try {
         statement=connection.createStatement();
         System.out.println("Quel est le nom de l'article?: ");
         String nom=scanner.nextLine();
         System.out.println("Quel est son nouveau prix: ");
         int newprix=scanner.nextInt();
         if (newprix<=0) {
            System.out.println("Veuillez saisir un prix valide!!!");
         }
         String query = "SELECT quantite FROM inventaire WHERE nom ='"+nom+"'";
         resultset=statement.executeQuery(query);
         String sql="UPDATE inventaire SET prix=? WHERE nom=?";
         preparedStatement=connection.prepareStatement(sql);
         preparedStatement.setFloat(1, newprix);
         preparedStatement.setString(2, nom);
         preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
             statement.close();
             preparedStatement.close();
             connection.close();    
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        }
    }
}
