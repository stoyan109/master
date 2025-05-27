package model;

	import java.time.LocalDate;

	import java.time.format.DateTimeFormatter;

	public class Artwork {

	    private String name;

	    private Artist author;
	    private String genre;
	    private LocalDate creationDate;
	    
	    public Artwork(String name, Artist author, String genre, LocalDate creationDate) {
	        validateName(name);
	        validateAuthor(author);
	        validateGenre(genre);
	        validateCreationDate(creationDate);
	        this.name = name;
	        this.author = author;
	        this.genre = genre;
	        this.creationDate = creationDate;
	    }

	    private void validateName(String name) {
	        if (name == null || name.trim().isEmpty()) {
	            throw new IllegalArgumentException("Artwork name cannot be empty");
	        }
	    }

	    private void validateAuthor(Artist author) {
	        if (author == null) {
	            throw new IllegalArgumentException("Artwork must have an author");
	        }

	    }

	    private void validateGenre(String genre) {
	        if (genre == null || genre.trim().isEmpty()) {
	            throw new IllegalArgumentException("Genre cannot be empty");

	        }

	    }

	    private void validateCreationDate(LocalDate creationDate) {
	        if (creationDate == null || creationDate.isAfter(LocalDate.now())) {
	            throw new IllegalArgumentException("Invalid creation date");

	        }

	    }

	    public String getName() { return name; }
	    public Artist getAuthor() { return author; }
	    public String getGenre() { return genre; }
	    public LocalDate getCreationDate() { return creationDate; }
	  
	    public void setName(String name) {
	        validateName(name);
	        this.name = name;
	    }

	    public void setAuthor(Artist author) {
	        validateAuthor(author);
	        this.author = author;
	    }
	    public void setGenre(String genre) {
	        validateGenre(genre);
	        this.genre = genre;
	    }

	    public void setCreationDate(LocalDate creationDate) {

	        validateCreationDate(creationDate);

	        this.creationDate = creationDate;

	    }
	    
	    public boolean equals(Artwork other) {
	        if (other == null) return false;
	        return this.name.equals(other.name) && 
	               this.author.equals(other.author) && 
	               this.creationDate.equals(other.creationDate);
	    }
	  
	    public String toString() {
	        return String.format("%s by %s (%s, %s)", 
	           name, 
	           author.getName(), 
	           genre, 
	           creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
	    }	
	    public int compareByDate(Artwork other) {
	        if (this.creationDate.isBefore(other.creationDate)) {
	            return -1;
	        } else if (this.creationDate.isAfter(other.creationDate)) {
	            return 1;
	        } else {
	            return 0;
	        }

	    }

	}
	 


