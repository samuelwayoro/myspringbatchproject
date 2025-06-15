# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 11 : Intro au itemReader dans Spring batch </font>

Un ItemReader en Spring Batch est un composant qui lit une donnée à traiter, un élément à la fois (ligne de fichier, enregistrement de base de données, etc.).

✅ <font color=red>  À quoi ça sert ? </font>

C’est le point d’entrée du traitement chunk-oriented.
Il lit un "item" (objet) et le passe au ItemProcessor (facultatif), puis au ItemWriter.

🧱 Interface

    public interface ItemReader<T> {
        T read() throws Exception;
    }

* Il lit un élément par appel à read(). 
* Quand il n’y a plus rien à lire, il retourne null.

<font color=red> NB : </font> Toute itemreader implémente l'interface itemReader < T >

📌 Exemple 1 : ListItemReader (lecture en mémoire d'une liste d'entier ) 

- notre classe d'exemple : FirstItemReader


📌 Exemple 2 : ListItemReader (lecture en mémoire d'une liste de chaîne de caractères ) 


        @Bean
        public ItemReader<String> reader() {
            List<String> noms = List.of("Alice", "Bob", "Charlie");
            return new ListItemReader<>(noms);
        }

Ici, Spring Batch va lire "Alice", puis "Bob", puis "Charlie", puis null (marquant la fin de la boucle).


📌 Exemple 3 : FlatFileItemReader (lecture d’un fichier CSV ligne par ligne)

        @Bean
        public FlatFileItemReader<String> reader() {
            FlatFileItemReader<String> reader = new FlatFileItemReader<>();
            reader.setResource(new FileSystemResource("clients.csv"));
            reader.setLineMapper((line, lineNumber) -> line); // chaque ligne est une String brute
            return reader;
        }
Ce lecteur lit un fichier texte et retourne chaque ligne l’une après l’autre.

📌 Exemple 4 : JdbcCursorItemReader (lecture en base de données)

        @Bean
        public JdbcCursorItemReader<Client> reader(DataSource dataSource) {
            JdbcCursorItemReader<Client> reader = new JdbcCursorItemReader<>();
            reader.setDataSource(dataSource);
            reader.setSql("SELECT id, nom FROM client");
            reader.setRowMapper((rs, rowNum) ->
                new Client(rs.getLong("id"), rs.getString("nom"))
            );
            return reader;
        }

Ici, on lit les enregistrements de la table client un par un.

🧠 Résumé :

| Type de Reader         | Source                              |
|------------------------|-------------------------------------|
| `ListItemReader`       | Liste en mémoire                    |
| `FlatFileItemReader`   | Fichier CSV / TXT                   |
| `JdbcCursorItemReader` | Requête SQL                         |
| `JpaPagingItemReader`  | Entités JPA                         |
| `CustomReader`         | Écrit à la main (API externe, etc.) |


