

package util;

import model.Artwork;
import model.Artist;
import model.ExhibitionManager;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class FileHandler {
   private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
   private static final String ARTWORKS_FILE = "artworks.txt";
   private static final String ARTISTS_FILE = "artists.txt";
   public static void saveData(ExhibitionManager manager) throws IOException {
       saveArtists(manager.getAllArtists());
       saveArtworks(manager.getAllArtworks());
   }
   public static ExhibitionManager loadData() throws IOException {
       ExhibitionManager manager = new ExhibitionManager();
       loadArtists(manager);
       loadArtworks(manager);
       return manager;
   }
   private static void saveArtists(List<Artist> artists) throws IOException {
       try (PrintWriter writer = new PrintWriter(new FileWriter(ARTISTS_FILE))) {
           for (Artist artist : artists) {
               writer.println(artist.getName());
               writer.println(artist.getNationality());
           }
       }
   }
   private static void saveArtworks(List<Artwork> artworks) throws IOException {
       try (PrintWriter writer = new PrintWriter(new FileWriter(ARTWORKS_FILE))) {
           for (Artwork artwork : artworks) {
               writer.println(artwork.getName());
               writer.println(artwork.getAuthor().getName());
               writer.println(artwork.getGenre());
               writer.println(artwork.getCreationDate().format(DATE_FORMATTER));
           }
       }
   }
   private static void loadArtists(ExhibitionManager manager) throws IOException {
       List<String> lines = readAllLines(ARTISTS_FILE);
       for (int i = 0; i < lines.size(); i += 2) {
           String name = lines.get(i);
           String nationality = lines.get(i + 1);
           manager.addArtist(new Artist(name, nationality));
       }
   }
   private static void loadArtworks(ExhibitionManager manager) throws IOException {
       List<String> lines = readAllLines(ARTWORKS_FILE);
       for (int i = 0; i < lines.size(); i += 4) {
           String name = lines.get(i);
           String authorName = lines.get(i + 1);
           String genre = lines.get(i + 2);
           LocalDate date = LocalDate.parse(lines.get(i + 3), DATE_FORMATTER);
           Artist author = findArtist(manager, authorName);
           if (author == null) {
               throw new IOException("Artist not found for artwork: " + name);
           }
           manager.addArtwork(new Artwork(name, author, genre, date));
       }
   }
   private static Artist findArtist(ExhibitionManager manager, String name) {
       for (Artist artist : manager.getAllArtists()) {
           if (artist.getName().equals(name)) {
               return artist;
           }
       }
       return null;
   }
   private static List<String> readAllLines(String filename) throws IOException {
       List<String> lines = new ArrayList<>();
       try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
           String line;
           while ((line = reader.readLine()) != null) {
               lines.add(line);
           }
       }
       return lines;
   }
}