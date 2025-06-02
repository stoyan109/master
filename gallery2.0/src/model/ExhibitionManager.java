package model;


import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

public class ExhibitionManager {
    private Map<String, List<Artwork>> artworksByGenre;
    private Map<String, List<Artwork>> artworksByAuthor;
    private List<Artwork> allArtworks;
    private List<Artist> artists;
    public ExhibitionManager() {
        artworksByGenre = new HashMap<>();
        artworksByAuthor = new HashMap<>();
        allArtworks = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public void addArtwork(Artwork artwork) {
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork cannot be null");
        }
        if (allArtworks.contains(artwork)) {
            throw new IllegalArgumentException("Artwork already exists in the gallery");
        }
        // Add to all artworks
        allArtworks.add(artwork);
        // Add to genre map
        String genre = artwork.getGenre();
        artworksByGenre.computeIfAbsent(genre, k -> new ArrayList<>()).add(artwork);
        // Add to author map
        String authorName = artwork.getAuthor().getName();
        artworksByAuthor.computeIfAbsent(authorName, k -> new ArrayList<>()).add(artwork);
        // Add to artist's collection
        artwork.getAuthor().addArtwork(artwork);
    }
    public void removeArtwork(Artwork artwork) {
        if (!allArtworks.remove(artwork)) {
            throw new IllegalArgumentException("Artwork not found in gallery");
        }
        // Remove from genre map
        String genre = artwork.getGenre();
        if (artworksByGenre.containsKey(genre)) {
            artworksByGenre.get(genre).remove(artwork);
            if (artworksByGenre.get(genre).isEmpty()) {
                artworksByGenre.remove(genre);
            }
        }
        String authorName = artwork.getAuthor().getName();
        if (artworksByAuthor.containsKey(authorName)) {
            artworksByAuthor.get(authorName).remove(artwork);
            if (artworksByAuthor.get(authorName).isEmpty()) {
                artworksByAuthor.remove(authorName);
            }
        }
   
    
        artwork.getAuthor().removeArtwork(artwork);
    }
    // Artist management
    public void addArtist(Artist artist) {
        if (artist == null) {
            throw new IllegalArgumentException("Artist cannot be null");
        }
        if (artists.contains(artist)) {
            throw new IllegalArgumentException("Artist already exists in the gallery");
        }
        artists.add(artist);
    }
    public void removeArtist(Artist artist) {
        if (!artists.remove(artist)) {
            throw new IllegalArgumentException("Artist not found in gallery");
        }
        // Remove all artworks by this artist

        List<Artwork> artistArtworks = new ArrayList<>(artist.getArtworks());

        for (Artwork artwork : artistArtworks) {
            removeArtwork(artwork);
        }
    }
    // Search methods
    public List<Artwork> searchByAuthor(String authorName) {
        List<Artwork> result = new ArrayList<>();
        for (Artwork artwork : allArtworks) {
            if (artwork.getAuthor().getName().equalsIgnoreCase(authorName)) {
                result.add(artwork);
            }
        }
        return result;
    }
    public List<Artwork> searchByTitle(String title) {
        List<Artwork> result = new ArrayList<>();
        for (Artwork artwork : allArtworks) {
            if (artwork.getName().equalsIgnoreCase(title)) {
                result.add(artwork);
            }
        }
        return result;
    }
    // Custom sorting method (Insertion Sort)
    public List<Artwork> sortByDate() {
        List<Artwork> sorted = new ArrayList<>(allArtworks);
        for (int i = 1; i < sorted.size(); i++) {
            Artwork current = sorted.get(i);
            int j = i - 1;
            while (j >= 0 && sorted.get(j).compareByDate(current) > 0) {
                sorted.set(j + 1, sorted.get(j));
                j--;
            }
            sorted.set(j + 1, current);
        }
        return sorted;
    }
    // Getters
    public List<Artwork> getAllArtworks() { return new ArrayList<>(allArtworks); }
    public List<Artist> getAllArtists() { return new ArrayList<>(artists); }
    public Map<String, List<Artwork>> getArtworksByGenre() { return new HashMap<>(artworksByGenre); }
    public Map<String, List<Artwork>> getArtworksByAuthor() { return new HashMap<>(artworksByAuthor); }

}
 