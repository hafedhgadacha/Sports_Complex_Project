/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipement;

/**
 *
 * @author MSI PC
 */import java.util.Scanner;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Equipement {
    private int equipement_id_match;
    private int equipement_id;
    private String typeMateriel;
    private int nbrEnStock;
    private int nbrUtilisee;
    private float prix;
    public Equipement(){};
        public Equipement(int equipement_id){this.equipement_id= equipement_id;};
    public Equipement(int equipement_id,String typeMateriel, int nbrUtilisee, float prix,int equipement_id_match) {
        this.equipement_id=equipement_id;
        this.typeMateriel = typeMateriel;
        this.nbrEnStock = 0;
        this.nbrUtilisee = nbrUtilisee;
        this.prix = prix;
        this.equipement_id_match=equipement_id_match;
    } 
    public Equipement(int equipement_id,String typeMateriel, int nbrEnStock, float prix) {
        this.equipement_id=equipement_id;
        this.typeMateriel = typeMateriel;
        this.nbrEnStock = nbrEnStock;
         this.prix = prix;
    }
    public void setequipement_id_match(int a)
    {
        equipement_id_match=a;
    }
    
    
    public Equipement(String typeMateriel, int nbrEnStock, float prix) {
        this.typeMateriel = typeMateriel;
        this.nbrEnStock = nbrEnStock;
        this.nbrUtilisee = 0;
        this.prix = prix;
    }
     public void saisir() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("id");
        equipement_id = scanner.nextInt();
        scanner.nextLine();
                
        System.out.print("Type de matériel : ");
        typeMateriel = scanner.nextLine();

        System.out.print("Nombre en stock : ");
        nbrEnStock = scanner.nextInt();
        scanner.nextLine();  

        System.out.print("Nombre utilisé : ");
        nbrUtilisee = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Prix : ");
        prix = scanner.nextFloat();
        scanner.nextLine();
    }

    public String getTypeMateriel() {
        return typeMateriel;
    }

    public void setTypeMateriel(String typeMateriel) {
        this.typeMateriel = typeMateriel;
    }

    public int getNbrEnStock() {
        return nbrEnStock;
    }
    public int getequipement_id_match() {
        return equipement_id_match;
    }

    public void setNbrEnStock(int nbrEnStock) {
        this.nbrEnStock = nbrEnStock;
    }
    public int getequipement_id() {
        return equipement_id;
    }

    public void setequipement_id(int equipement_id) {
        this.equipement_id = equipement_id;
    }

    public int getNbrUtilisee() {
        return nbrUtilisee;
    }

    public void setNbrUtilisee(int nbrUtilisee) {
        this.nbrUtilisee = nbrUtilisee;
    }
    
    public float getPrix(){
        return prix;
    }
    
    public void setPrix(float prix) {
        this.prix= prix;
    }

    public void modifierEquipement() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sélectionnez l'attribut à modifier :");
        System.out.println("1. Type de matériel");
        System.out.println("2. Nombre en stock");
        System.out.println("3. Nombre utilisé");
        System.out.println("4. Prix");
        System.out.println("5. id quipement");

        int choix = scanner.nextInt();

        switch (choix) {
            case 1:
                System.out.print("Nouveau type de matériel : ");
                typeMateriel = scanner.next();
                break;
            case 2:
                System.out.print("Nouveau nombre en stock : ");
                nbrEnStock = scanner.nextInt();
                break;
            case 3:
                System.out.print("Nouveau nombre utilisé : ");
                nbrUtilisee = scanner.nextInt();
                break;
            case 4:
                System.out.print("Nouveau prix : ");
                prix = scanner.nextFloat();
                break;
            case 5:
                 System.out.print("id ");
                equipement_id = scanner.nextInt();
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }
    


    public FloatProperty PrixProperty() {
        return new SimpleFloatProperty(this, "Prix", prix);
    }

    public IntegerProperty nbrUtiliseeProperty() {
        return new SimpleIntegerProperty(this, "Nombre utilisé", nbrUtilisee);
    }

    public IntegerProperty nbrEnStockProperty() {
        return new SimpleIntegerProperty(this, "Nombre en stock", nbrEnStock);
    }
    public IntegerProperty equipement_idProperty() {
        return new SimpleIntegerProperty(this, "equipement_id", equipement_id);
    }
    public IntegerProperty equipement_id_matchProperty() {
        return new SimpleIntegerProperty(this, "equipement_id_match", equipement_id_match);
    }

        
    public StringProperty typeMaterielProperty() {
        return new SimpleStringProperty(this, "matériel", typeMateriel);
    }


    public void afficherEquipement() {
        System.out.println("Type de matériel : " + typeMateriel);
        System.out.println("Nombre en stock : " + nbrEnStock);
        System.out.println("Nombre utilisé : " + nbrUtilisee);
        System.out.println("Prix : " + prix);
        System.out.println("equipement id : " + equipement_id);
    }}