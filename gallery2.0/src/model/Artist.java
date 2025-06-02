package model;

import java.util.ArrayList;

import java.util.List;

public class Artist {
    private String name;
    private String nationality;
    private List<Artwork> artworks;
    public Artist(String name, String nationality) {
        validateName(name);
        validateNationality(nationality);
        this.name = name;
        this.nationality = nationality;
        this.artworks = new ArrayList<>();

    }
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Artist name cannot be empty");
        }
    }
    private void validateNationality(String nationality) {
        if (nationality == null || nationality.trim().isEmpty()) {
            throw new IllegalArgumentException("Nationality cannot be empty");
        }
    }


    public String getName() { return name; }
    
    public String getNationality() { return nationality; }
    
    public List<Artwork> getArtworks() { return new ArrayList<>(artworks); }
    
    public void setName(String name) {

        validateName(name);
      
        this.name = name;
    }
    public void setNationality(String nationality) {
        validateNationality(nationality);
        this.nationality = nationality;
    }

    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null");
        }
        if (artworks.contains(artwork)) {
            throw new IllegalArgumentException("This artwork is already associated with the artist");
        }
        artworks.add(artwork);
    }
    public void removeArtwork(Artwork artwork) {
        if (!artworks.remove(artwork)) {
            throw new IllegalArgumentException("Artwork not found in artist's collection");
        }
    }

    public boolean equals(Artist other) {
        if (other == null) return false;
        return this.name.equals(other.name) && 
               this.nationality.equals(other.nationality);
    }
    public String toString() {
        return String.format("%s (%s)", name, nationality);
    }

}