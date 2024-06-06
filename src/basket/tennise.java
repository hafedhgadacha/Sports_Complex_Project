/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basket;
import tennis.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author MSI PC
 */

import java.util.Scanner;
import javafx.beans.property.*;
import athelete.personne;
public class tennise extends personne {
    

    private int age;
    private String nom_equipe;
    public tennise(){};
    public tennise(int id,String nom, String prenom,int age, String nom_equipe) {
        super(id,nom, prenom);
        this.age = age;
        this.nom_equipe = nom_equipe;
    }

    public tennise(int id,String nom, String prenom) { super(id,nom, prenom);
        
    }
    

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNomEquipe() {
        return nom_equipe;
    }

    public void setNomEquipe(String nom_equipe) {
        this.nom_equipe = nom_equipe;
    }

public void modifierAthlete() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez l'attribut à modifier :");
        System.out.println("1. Nom");
        System.out.println("2. Prénom");
        System.out.println("3. Âge");
        System.out.println("4. Nom de l'Équipe");
        System.out.println("5. id");
        

        int choix = scanner.nextInt();
        scanner.nextLine(); 

        switch (choix) {
            case 1:
                System.out.print("Nouveau nom : ");
                setNom(scanner.nextLine());
                break;
            case 2:
                System.out.print("Nouveau prénom : ");
                setPrenom(scanner.nextLine());
                break;
            case 3:
                System.out.print("Nouvel âge : ");
                age = scanner.nextInt();
                break;
            case 4:
                System.out.print("Nouveau nom d'équipe : ");
                nom_equipe = scanner.nextLine();
                break;
         
            case 5:
                System.out.print("Nouvel id : ");
                setid( scanner.nextInt());
                break;

            default:
                System.out.println("Choix invalide.");
        }
    }
    public void afficherAthlete() {
        System.out.println("id: " + getid());
        System.out.println("Nom: " + getNom());
        System.out.println("Prenom: " + getPrenom());
        System.out.println("Âge: " + age);
        System.out.println("Nom de l'Équipe: " + nom_equipe);
    }

    public void saisirAthlete(int id ,String nom, String prenom, int age, String nom_equipe) {
        setid(id);
        setNom(nom);
        setPrenom(prenom);
        this.age = age;
        this.nom_equipe = nom_equipe;
    }
    
     public void saisirAthlete() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("id de l'athlète : ");
        setid( scanner.nextInt());
        
        System.out.print("Nom de l'athlète : ");
        setNom(scanner.nextLine());

        System.out.print("Prénom de l'athlète : ");
        setPrenom(scanner.nextLine());

        System.out.print("Âge de l'athlète : ");
        this.age = scanner.nextInt();
        
        scanner.nextLine(); // Pour consommer la ligne en cours

        System.out.print("Nom de l'équipe de l'athlète : ");
        this.nom_equipe = scanner.nextLine();
     }
     
 
    public IntegerProperty ageProperty() {
        return new SimpleIntegerProperty(this, "age", age);
    }

    public StringProperty nomEquipeProperty() {
        return new SimpleStringProperty(this, "nomEquipe", nom_equipe);
    }


}

    

