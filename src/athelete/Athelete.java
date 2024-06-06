
package athelete;
import java.util.Scanner;
import javafx.beans.property.*;


  public class Athelete extends personne {
    private String filliere;
    private String poste ;
    private int num_maillot;
    private int age;
    private String nom_equipe;
    private String capitaine;
    public Athelete(){};
    public Athelete(int id,String nom, String prenom, String type,String poste, int num_maillot, int age, String nom_equipe, String capitaine) {
        super(id,nom, prenom);
        this.filliere = type;
        this.poste = poste;
        this.num_maillot = num_maillot;
        this.age = age;
        this.nom_equipe = nom_equipe;
        this.capitaine = capitaine;
    }

    public Athelete(int id,String nom, String prenom) { super(id,nom, prenom);
        
    }
     public String getfilliere() {
        return filliere;
    }

    public void setfilliere(String type) {
        this.filliere = type;
    }
    public String getposte() {
        return poste;
    }

    public void setposte(String poste) {
        this.poste = poste;
    }

    public int getNumMaillot() {
        return num_maillot;
    }

    public void setNumMaillot(int num_maillot) {
        this.num_maillot = num_maillot;
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

    public String isCapitaine() {
        return capitaine;
    }

    public void setCapitaine(String capitaine) {
        this.capitaine = capitaine;
    }
public void modifierAthlete() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez l'attribut à modifier :");
        System.out.println("1. Nom");
        System.out.println("2. Prénom");
        System.out.println("3. filliere");
        System.out.println("4. Numéro de Maillot");
        System.out.println("5. Âge");
        System.out.println("6. Nom de l'Équipe");
        System.out.println("7. Statut de Capitaine");
        System.out.println("8. id");
        System.out.println("9. poste");
        

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
                System.out.print("Nouveau filliere : ");
                filliere = scanner.nextLine();
                break;
            case 4:
                System.out.print("Nouveau numéro de maillot : ");
                num_maillot = scanner.nextInt();
                break;
            case 5:
                System.out.print("Nouvel âge : ");
                age = scanner.nextInt();
                break;
            case 6:
                System.out.print("Nouveau nom d'équipe : ");
                nom_equipe = scanner.nextLine();
                break;
            case 7:
                System.out.print("Nouveau statut de capitaine (true/false) : ");
                capitaine = scanner.nextLine();
                break;
            case 8:
                System.out.print("Nouvel id : ");
                setid( scanner.nextInt());
                break;
           case 9:
                System.out.print("Nouveau poste : ");
                poste = scanner.nextLine();
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }
    public void afficherAthlete() {
        System.out.println("id: " + getid());
        System.out.println("Nom: " + getNom());
        System.out.println("Prenom: " + getPrenom());
        System.out.println("filliere: " + filliere);
        System.out.println("Poste: " + poste);
        System.out.println("Numéro de Maillot: " + num_maillot);
        System.out.println("Âge: " + age);
        System.out.println("Nom de l'Équipe: " + nom_equipe);
        System.out.println("Capitaine: " + capitaine );
    }

    public void saisirAthlete(int id ,String nom, String prenom, String filliere,String poste, int num_maillot, int age, String nom_equipe, String capitaine) {
        setid(id);
        setNom(nom);
        setPrenom(prenom);
        this.filliere = filliere;
        this.poste = poste;
        this.num_maillot = num_maillot;
        this.age = age;
        this.nom_equipe = nom_equipe;
        this.capitaine = capitaine;
    }
    
     public void saisirAthlete() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("id de l'athlète : ");
        setid( scanner.nextInt());
        
        System.out.print("Nom de l'athlète : ");
        setNom(scanner.nextLine());

        System.out.print("Prénom de l'athlète : ");
        setPrenom(scanner.nextLine());

        System.out.print("filliere de l'athlète : ");
        this.filliere = scanner.nextLine();
        
        System.out.print("poste de l'athlète : ");
        this.poste = scanner.nextLine();

        System.out.print("Numéro de maillot de l'athlète : ");
        this.num_maillot = scanner.nextInt();

        System.out.print("Âge de l'athlète : ");
        this.age = scanner.nextInt();
        
        scanner.nextLine(); // Pour consommer la ligne en cours

        System.out.print("Nom de l'équipe de l'athlète : ");
        this.nom_equipe = scanner.nextLine();

        System.out.print("Statut de capitaine de l'athlète (true/false) : ");
        this.capitaine = scanner.nextLine();
    }
     
      public StringProperty posteProperty() {
        return new SimpleStringProperty(this, "poste", poste);
    }
     public StringProperty filliereProperty() {
        return new SimpleStringProperty(this, "filliere", filliere);
    }

    public IntegerProperty numMaillotProperty() {
        return new SimpleIntegerProperty(this, "numMaillot", num_maillot);
    }

    public IntegerProperty ageProperty() {
        return new SimpleIntegerProperty(this, "age", age);
    }

    public StringProperty nomEquipeProperty() {
        return new SimpleStringProperty(this, "nomEquipe", nom_equipe);
    }

    public StringProperty capitaineProperty() {
        return new SimpleStringProperty(this, "capitaine", capitaine);
    }

}
