package match;

import java.util.Scanner;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class match {
    private int id ;
    private String nomEquipe1;
    private String nomEquipe2;
    private String arbitre;
    private String resultat;

    public match(){};
    public match(int id,String nomEquipe1, String nomEquipe2, String arbitre, String resultat) {
        this.id = id ;
        this.nomEquipe1 = nomEquipe1;
        this.nomEquipe2 = nomEquipe2;
        this.arbitre = arbitre;
        this.resultat = resultat;    
    }

    public String getNomEquipe1() {
        return nomEquipe1;
    }

    public String getNomEquipe2() {
        return nomEquipe2;
    }

    public String getArbitre() {
        return arbitre;
    }

    public String getResultat() {
        return resultat;
    }


    public int getid() {
        return id;
    }
    public void setid(int a) {
         id = a;
    }



    public void setNomEquipe1(String nomEquipe1) {
        this.nomEquipe1 = nomEquipe1;
    }

    public void setNomEquipe2(String nomEquipe2) {
        this.nomEquipe2 = nomEquipe2;
    }
    public void setarbitre(String arbitre) {
        this.arbitre = arbitre;
    }


    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

 public void modifierMatch() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez l'attribut à modifier :");
        System.out.println("1. Nom de l'équipe 1");
        System.out.println("2. Nom de l'équipe 2");
        System.out.println("3. Arbitre");
        System.out.println("4. Résultat");
        System.out.println("5. id de match");

        int choix = scanner.nextInt();
        scanner.nextLine();  
        switch (choix) {
            case 1:
                System.out.print("Nouveau nom de l'équipe 1 : ");
                nomEquipe1 = scanner.nextLine();
                break;
            case 2:
                System.out.print("Nouveau nom de l'équipe 2 : ");
                nomEquipe2 = scanner.nextLine();
                break;
            case 3:
                System.out.print("Nouveau nom de arbitre : ");
                arbitre = scanner.nextLine();
                break;
            case 4:
                System.out.print("Nouveau résultat : ");
                resultat = scanner.nextLine();
                break;
          
            case 5:
                System.out.print("Nouveau id : ");
                setid( scanner.nextInt());
                break;
           
            default:
                System.out.println("Choix invalide.");
                break;
        }
    }
    
     public void afficher() {


        System.out.println("Nom de l'id : " + id);
        System.out.println("Nom de l'équipe 1 : " + nomEquipe1);
        System.out.println("Nom de l'équipe 2 : " + nomEquipe2);
        System.out.println("Arbitre : " + arbitre); 
        System.out.println("Résultat : " + resultat);

    }
     public void saisirMatch() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nom de l'équipe 1 : ");
        nomEquipe1 = scanner.nextLine();

        System.out.print("Nom de l'équipe 2 : ");
        nomEquipe2 = scanner.nextLine();

        System.out.print("Nom de l'arbitre : ");
        arbitre = scanner.nextLine();
        
        
        System.out.print("Résultat : ");
        resultat = scanner.nextLine();

        }
     public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(this, "id", id);
    }

    public StringProperty nomEquipe1Property() {
        return new SimpleStringProperty(this, "nomEquipe1", nomEquipe1);
    }
    public StringProperty nomEquipe2Property() {
        return new SimpleStringProperty(this, "nomEquipe2", nomEquipe2);
    }
    public StringProperty resultatProperty() {
        return new SimpleStringProperty(this, "resultat", resultat);
    }
    public StringProperty arbitreProperty() {
        return new SimpleStringProperty(this, "arbitre", arbitre);
    }
}
