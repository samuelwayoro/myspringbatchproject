# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 22 : Configurer un JSONItemReader</font>

<font color=red> 📘 Qu'est-ce que JSONItemReader ? </font>

Pour lire des données depuis un fichier JSON dans un projet Spring Batch, on utilise un composant appelé JsonItemReader<T>.

Voici comment le configurer étape par étape pour qu’il lise un fichier .json ligne par ligne et désérialise chaque objet en une instance Java.

👉 Il lit chaque ligne et la transforme en objet Java de type T.


✅ 1. Structure du fichier JSON attendue

Le JsonItemReader lit un fichier JSON ligne par ligne (chaque ligne contient un objet JSON complet) :

    {"id": 1, "nom": "Alice"}
    {"id": 2, "nom": "Bob"}

Chaque ligne est un objet JSON distinct.




✅ 2. Crée ta classe modèle


    public class Personne {
    private int id;
    private String nom;

    // Getters & setters
}


✅ 3. Déclare ton JsonItemReader dans une méthode @Bean

    @Bean
    public JsonItemReader<Personne> jsonItemReader() {
      return new JsonItemReaderBuilder<Personne>()
                .name("personneJsonItemReader")
                .jsonObjectReader(new JacksonJsonObjectReader<>(Personne.class))
                .resource(new FileSystemResource("input/personnes.json"))
                .build();
    }


<i> 🔍 Le fichier doit être situé dans le répertoire /input/ à la racine du projet ou selon le chemin réel fourni en paramètre de la méthode jsonItemReader.</i>


✅ 4. Utilise-le dans un step chunk-oriented


    @Bean
    public Step lectureJsonStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
      return new StepBuilder("lectureJsonStep", jobRepository)
              .<Personne, Personne>chunk(10, transactionManager)
              .reader(jsonItemReader())
              .writer(items -> items.forEach(System.out::println)) // Writer d'exemple
              .build();
    }

✅ 5. Ajoute le step au job

    @Bean
    public Job monJob(JobRepository jobRepository, Step lectureJsonStep) {
      return new JobBuilder("jobLectureJson", jobRepository)
        .start(lectureJsonStep)
        .build();
    }


🧠 Remarques
:
- JsonItemReader lit des objets JSON par ligne, pas un tableau [ {…}, {…} ]

- Pour lire un tableau JSON complet, il faut un reader personnalisé

