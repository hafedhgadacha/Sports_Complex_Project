/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package athelete;
import javafx.beans.property.*;


public class personne {
    private int id;
    private String nom;
    private String prenom;
    
    public personne(int id ,String nom, String prenom) {
       this.id=id;
        this.nom = nom;
        this.prenom = prenom;
    }
    public personne(){};
        public personne(String nom, String prenom){this.nom = nom;
        this.prenom = prenom;};

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public IntegerProperty idProperty() {
    return new SimpleIntegerProperty(this, "id", id);
}

public StringProperty prenomProperty() {
    return new SimpleStringProperty(this, "prenom", prenom);}
public StringProperty nomProperty() {
    return new SimpleStringProperty(this, "nom", nom);
}

// Repeat for other properties...

}
