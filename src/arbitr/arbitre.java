package arbitr;

import java.util.Scanner;
import athelete.personne;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class arbitre extends personne {
    private String type;
    private int age;
    
    public arbitre(int id,String nom, String prenom){super(id,nom, prenom);};
    public arbitre (){};
        public arbitre (String nom, String prenom,String type){
            super(nom, prenom);
            this.type=type;};

    public arbitre(int id,String nom, String prenom, String type, int age) {
        super(id,nom, prenom);
        this.type = type;
        this.age = age;
    }
 public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
public void modifierArbitre() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez l'attribut à modifier :");
        System.out.println("1. Nom");
        System.out.println("2. Prénom");
        System.out.println("3. Type");
        System.out.println("4. Âge");
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
                System.out.print("Nouveau type : ");
                type = scanner.nextLine();
                break;
            case 4:
                System.out.print("Nouvel âge : ");
                age = scanner.nextInt();
                break;
            case 5:
                System.out.print("Nouvel id : ");
                setid( scanner.nextInt());
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void afficherarbitre() {
        System.out.println("id: " + getid());
        System.out.println("Nom: " + getNom());
        System.out.println("Prenom: " + getPrenom());
        System.out.println("Type: " + type);
        System.out.println("Âge: " + age);
        
    }

    public void saisirarbitre(int id,String nom, String prenom, String type, int age) {
        setid(id);
        setNom(nom);
        setPrenom(prenom);
        this.type = type;
        this.age = age;
    }
    public void saisirArbitre() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Type d'arbitre : ");
    type = scanner.nextLine();

    System.out.print("Âge de l'arbitre : ");
    age = scanner.nextInt();
    scanner.nextLine();  // Clear the newline character

    System.out.print("ID de l'arbitre : ");
    setid(scanner.nextInt());
    scanner.nextLine();  // Clear the newline character

    System.out.print("Nom de l'arbitre : ");
    setNom(scanner.nextLine());

    System.out.print("Prénom de l'arbitre : ");
    setPrenom(scanner.nextLine());
}
    
    public IntegerProperty ageProperty() {
        return new SimpleIntegerProperty(this, "age", age);
    }

    public StringProperty typeProperty() {
        return new SimpleStringProperty(this, "type", type);
    }

}
