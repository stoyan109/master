 

 package view;

import model.Artwork; 

import model.Artist; 

import model.ExhibitionManager; 

import util.FileHandler; 

 

import javax.swing.*; 

import java.awt.*; 

import java.awt.event.ActionEvent; 

import java.awt.event.ActionListener; 

import java.io.IOException; 

import java.time.LocalDate; 

import java.util.List; 

 

public class GUI extends JFrame { 

    private ExhibitionManager manager; 

    private DefaultListModel<Artwork> artworkListModel; 

    private DefaultListModel<Artist> artistListModel; 

 

    public GUI() { 

        try { 

            manager = FileHandler.loadData(); 

        } catch (IOException e) { 

            manager = new ExhibitionManager(); 

            JOptionPane.showMessageDialog(this, "Could not load data: " + e.getMessage(),  

                "Error", JOptionPane.ERROR_MESSAGE); 

        } 

 

        setTitle("Art Gallery Exhibition Manager"); 

        setSize(800, 600); 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        setLayout(new BorderLayout()); 

 

        // Initialize models 

        artworkListModel = new DefaultListModel<>(); 

        artistListModel = new DefaultListModel<>(); 

        refreshLists(); 

 

        // Create components 

        JTabbedPane tabbedPane = new JTabbedPane(); 

        tabbedPane.addTab("Artworks", createArtworksPanel()); 

        tabbedPane.addTab("Artists", createArtistsPanel()); 

 

        add(tabbedPane, BorderLayout.CENTER); 

        add(createMenuBar(), BorderLayout.NORTH); 

    } 

 

    private JPanel createArtworksPanel() { 

        JPanel panel = new JPanel(new BorderLayout()); 

 

        // Artwork list 

        JList<Artwork> artworkList = new JList<>(artworkListModel); 

        artworkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 

        JScrollPane artworkScrollPane = new JScrollPane(artworkList); 

        panel.add(artworkScrollPane, BorderLayout.CENTER); 

 

        // Artwork controls 

        JPanel controlPanel = new JPanel(new GridLayout(1, 4)); 

         

        JButton addArtworkBtn = new JButton("Add Artwork"); 

        addArtworkBtn.addActionListener(e -> showAddArtworkDialog()); 

         

        JButton editArtworkBtn = new JButton("Edit Artwork"); 

        editArtworkBtn.addActionListener(e -> { 

            Artwork selected = artworkList.getSelectedValue(); 

            if (selected != null) { 

                showEditArtworkDialog(selected); 

            } 

        }); 

         

        JButton removeArtworkBtn = new JButton("Remove Artwork"); 

        removeArtworkBtn.addActionListener(e -> { 

            Artwork selected = artworkList.getSelectedValue(); 

            if (selected != null) { 

                try { 

                    manager.removeArtwork(selected); 

                    refreshLists(); 

                } catch (IllegalArgumentException ex) { 

                    JOptionPane.showMessageDialog(this, ex.getMessage(),  

                        "Error", JOptionPane.ERROR_MESSAGE); 

                } 

            } 

        }); 

         

        JButton sortArtworkBtn = new JButton("Sort by Date"); 

        sortArtworkBtn.addActionListener(e -> { 

            artworkListModel.clear(); 

            manager.sortByDate().forEach(artworkListModel::addElement); 

        }); 

 

        controlPanel.add(addArtworkBtn); 

        controlPanel.add(editArtworkBtn); 

        controlPanel.add(removeArtworkBtn); 

        controlPanel.add(sortArtworkBtn); 

        panel.add(controlPanel, BorderLayout.SOUTH); 

 

        return panel; 

    } 

 

    private JPanel createArtistsPanel() { 

        JPanel panel = new JPanel(new BorderLayout()); 

 

        // Artist list 

        JList<Artist> artistList = new JList<>(artistListModel); 

        artistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 

        JScrollPane artistScrollPane = new JScrollPane(artistList); 

        panel.add(artistScrollPane, BorderLayout.CENTER); 

 

        // Artist controls 

        JPanel controlPanel = new JPanel(new GridLayout(1, 3)); 

         

        JButton addArtistBtn = new JButton("Add Artist"); 

        addArtistBtn.addActionListener(e -> showAddArtistDialog()); 

         

        JButton editArtistBtn = new JButton("Edit Artist"); 

        editArtistBtn.addActionListener(e -> { 

            Artist selected = artistList.getSelectedValue(); 

            if (selected != null) { 

                showEditArtistDialog(selected); 

            } 

        }); 

         

        JButton removeArtistBtn = new JButton("Remove Artist"); 

        removeArtistBtn.addActionListener(e -> { 

            Artist selected = artistList.getSelectedValue(); 

            if (selected != null) { 

                try { 

                    manager.removeArtist(selected); 

                    refreshLists(); 

                } catch (IllegalArgumentException ex) { 

                    JOptionPane.showMessageDialog(this, ex.getMessage(),  

                        "Error", JOptionPane.ERROR_MESSAGE); 

                } 

            } 

        }); 

 

        controlPanel.add(addArtistBtn); 

        controlPanel.add(editArtistBtn); 

        controlPanel.add(removeArtistBtn); 

        panel.add(controlPanel, BorderLayout.SOUTH); 

 

        return panel; 

    } 

 

    private JMenuBar createMenuBar() { 

        JMenuBar menuBar = new JMenuBar(); 

         

        // File menu 

        JMenu fileMenu = new JMenu("File"); 

         

        JMenuItem saveItem = new JMenuItem("Save"); 

        saveItem.addActionListener(e -> { 

            try { 

                FileHandler.saveData(manager); 

                JOptionPane.showMessageDialog(this, "Data saved successfully",  

                    "Success", JOptionPane.INFORMATION_MESSAGE); 

            } catch (IOException ex) { 

                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(),  

                    "Error", JOptionPane.ERROR_MESSAGE); 

            } 

        }); 

         

        JMenuItem exitItem = new JMenuItem("Exit"); 

        exitItem.addActionListener(e -> System.exit(0)); 

         

        fileMenu.add(saveItem); 

        fileMenu.addSeparator(); 

        fileMenu.add(exitItem); 

         

        // Search menu 

        JMenu searchMenu = new JMenu("Search"); 

         

        JMenuItem byAuthorItem = new JMenuItem("By Author"); 

        byAuthorItem.addActionListener(e -> { 

            String authorName = JOptionPane.showInputDialog(this, "Enter author name:"); 

            if (authorName != null && !authorName.trim().isEmpty()) { 

                List<Artwork> results = manager.searchByAuthor(authorName); 

                showSearchResults("Artworks by " + authorName, results); 

            } 

        }); 

         

        JMenuItem byTitleItem = new JMenuItem("By Title"); 

        byTitleItem.addActionListener(e -> { 

            String title = JOptionPane.showInputDialog(this, "Enter artwork title:"); 

            if (title != null && !title.trim().isEmpty()) { 

                List<Artwork> results = manager.searchByTitle(title); 

                showSearchResults("Artworks titled " + title, results); 

            } 

        }); 

         

        searchMenu.add(byAuthorItem); 

        searchMenu.add(byTitleItem); 

         

        menuBar.add(fileMenu); 

        menuBar.add(searchMenu); 

         

        return menuBar; 

    } 

 

    private void showAddArtworkDialog() { 

        JDialog dialog = new JDialog(this, "Add New Artwork", true); 

        dialog.setLayout(new GridLayout(5, 2)); 

         

        JTextField nameField = new JTextField(); 

        JComboBox<Artist> authorCombo = new JComboBox<>(); 

        manager.getAllArtists().forEach(authorCombo::addItem); 

        JTextField genreField = new JTextField(); 

        JTextField dateField = new JTextField(LocalDate.now().toString()); 

         

        dialog.add(new JLabel("Name:")); 

        dialog.add(nameField); 

        dialog.add(new JLabel("Author:")); 

        dialog.add(authorCombo); 

        dialog.add(new JLabel("Genre:")); 

        dialog.add(genreField); 

        dialog.add(new JLabel("Date (YYYY-MM-DD):")); 

        dialog.add(dateField); 

         

        JButton addButton = new JButton("Add"); 

        addButton.addActionListener(e -> { 

            try { 

                Artwork artwork = new Artwork( 

                    nameField.getText(), 

                    (Artist) authorCombo.getSelectedItem(), 

                    genreField.getText(), 

                    LocalDate.parse(dateField.getText()) 

                ); 

                manager.addArtwork(artwork); 

                refreshLists(); 

                dialog.dispose(); 

            } catch (Exception ex) { 

                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(),  

                    "Error", JOptionPane.ERROR_MESSAGE); 

            } 

        }); 

         

        JButton cancelButton = new JButton("Cancel"); 

        cancelButton.addActionListener(e -> dialog.dispose()); 

         

        dialog.add(addButton); 

        dialog.add(cancelButton); 

         

        dialog.pack(); 

        dialog.setLocationRelativeTo(this); 

        dialog.setVisible(true); 

    } 

 

    private void showEditArtworkDialog(Artwork artwork) { 

        JDialog dialog = new JDialog(this, "Edit Artwork", true); 

        dialog.setLayout(new GridLayout(5, 2)); 

         

        JTextField nameField = new JTextField(artwork.getName()); 

        JComboBox<Artist> authorCombo = new JComboBox<>(); 

        manager.getAllArtists().forEach(authorCombo::addItem); 

        authorCombo.setSelectedItem(artwork.getAuthor()); 

        JTextField genreField = new JTextField(artwork.getGenre()); 

        JTextField dateField = new JTextField(artwork.getCreationDate().toString()); 

         

        dialog.add(new JLabel("Name:")); 

        dialog.add(nameField); 

        dialog.add(new JLabel("Author:")); 

        dialog.add(authorCombo); 

        dialog.add(new JLabel("Genre:")); 

        dialog.add(genreField); 

        dialog.add(new JLabel("Date (YYYY-MM-DD):")); 

        dialog.add(dateField); 

         

        JButton saveButton = new JButton("Save"); 

        saveButton.addActionListener(e -> { 

            try { 

                artwork.setName(nameField.getText()); 

                artwork.setAuthor((Artist) authorCombo.getSelectedItem()); 

                artwork.setGenre(genreField.getText()); 

                artwork.setCreationDate(LocalDate.parse(dateField.getText())); 

                refreshLists(); 

                dialog.dispose(); 

            } catch (Exception ex) { 

                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(),  

                    "Error", JOptionPane.ERROR_MESSAGE); 

            } 

        }); 

         

        JButton cancelButton = new JButton("Cancel"); 

        cancelButton.addActionListener(e -> dialog.dispose()); 

         

        dialog.add(saveButton); 

        dialog.add(cancelButton); 

         

        dialog.pack(); 

        dialog.setLocationRelativeTo(this); 

        dialog.setVisible(true); 

    } 

 

    private void showAddArtistDialog() { 

        JDialog dialog = new JDialog(this, "Add New Artist", true); 

        dialog.setLayout(new GridLayout(3, 2)); 

         

        JTextField nameField = new JTextField(); 

        JTextField nationalityField = new JTextField(); 

         

        dialog.add(new JLabel("Name:")); 

        dialog.add(nameField); 

        dialog.add(new JLabel("Nationality:")); 

        dialog.add(nationalityField); 

         

        JButton addButton = new JButton("Add"); 

        addButton.addActionListener(e -> { 

            try { 

                Artist artist = new Artist( 

                    nameField.getText(), 

                    nationalityField.getText() 

                ); 

                manager.addArtist(artist); 

                refreshLists(); 

                dialog.dispose(); 

            } catch (Exception ex) { 

                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(),  

                    "Error", JOptionPane.ERROR_MESSAGE); 

            } 

        }); 

         

        JButton cancelButton = new JButton("Cancel"); 

        cancelButton.addActionListener(e -> dialog.dispose()); 

         

        dialog.add(addButton); 

        dialog.add(cancelButton); 

         

        dialog.pack(); 

        dialog.setLocationRelativeTo(this); 

        dialog.setVisible(true); 

    } 

 

    private void showEditArtistDialog(Artist artist) { 

        JDialog dialog = new JDialog(this, "Edit Artist", true); 

        dialog.setLayout(new GridLayout(3, 2)); 

         

        JTextField nameField = new JTextField(artist.getName()); 

        JTextField nationalityField = new JTextField(artist.getNationality()); 

         

        dialog.add(new JLabel("Name:")); 

        dialog.add(nameField); 

        dialog.add(new JLabel("Nationality:")); 

        dialog.add(nationalityField); 

         

        JButton saveButton = new JButton("Save"); 

        saveButton.addActionListener(e -> { 

            try { 

                artist.setName(nameField.getText()); 

                artist.setNationality(nationalityField.getText()); 

                refreshLists(); 

                dialog.dispose(); 

            } catch (Exception ex) { 

                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(),  

                    "Error", JOptionPane.ERROR_MESSAGE); 

            } 

        }); 

         

        JButton cancelButton = new JButton("Cancel"); 

        cancelButton.addActionListener(e -> dialog.dispose()); 

         

        dialog.add(saveButton); 

        dialog.add(cancelButton); 

         

        dialog.pack(); 

        dialog.setLocationRelativeTo(this); 

        dialog.setVisible(true); 

    } 

 

    private void showSearchResults(String title, List<Artwork> results) { 

        JDialog dialog = new JDialog(this, title, true); 

        dialog.setLayout(new BorderLayout()); 

         

        DefaultListModel<Artwork> model = new DefaultListModel<>(); 

        results.forEach(model::addElement); 

        JList<Artwork> resultList = new JList<>(model); 

         

        dialog.add(new JScrollPane(resultList), BorderLayout.CENTER); 

         

        JButton closeButton = new JButton("Close"); 

        closeButton.addActionListener(e -> dialog.dispose()); 

        dialog.add(closeButton, BorderLayout.SOUTH); 

         

        dialog.pack(); 

        dialog.setLocationRelativeTo(this); 

        dialog.setVisible(true); 

    } 

 

    private void refreshLists() { 

        artworkListModel.clear(); 

        manager.getAllArtworks().forEach(artworkListModel::addElement); 

         

        artistListModel.clear(); 

        manager.getAllArtists().forEach(artistListModel::addElement); 

    } 

} 