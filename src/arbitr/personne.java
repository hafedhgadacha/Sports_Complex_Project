/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbitr;
 import javafx.beans.property.*;

/////////////////////////////////////// 
public record personne(int id, String nom, String prenom) {
    
     public personne(int id ,String nom, String prenom) {
       this.id=id;
        this.nom = nom;
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
