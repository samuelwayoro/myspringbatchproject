# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 10 : Intro au Step chunck-oriented dans Spring batch </font>

Un chunk-oriented step est un type de traitement par lots, dans lequel Spring batch :

* lit un ensemble d'éléments (avec un itemReader)
* traite chaque élément (avec itemprocessor,optionel)
* Ecrit le tout d'un coup (avec un itemWriter)


✅ <font color=red> Principe de fonctionnement </font>

        [reader]    ------> [processor] -----> [writer]
            (xN fois)                           (écriture en bloc)


💡 Très utile pour :

* Lire un grand nombre de données 
* Eviter d'écrire à chaque ligne (meilleures performances)
* Gérer les transactions par chunck


🧠 <font color=red> Comment rajouter un step chunck-oriented dans un projet spring batch </font>

1. injecter les itemReader, itemProcessor et itemWriter dans la classe de configuration (annotée @Configuration), et les annoter @Autowired 
2. définir le step chunk oriented sous forme d'une méthode sans oublier : 
    * la taille du chunk <T, L> (avec T le type en entrée et le L le type en sortie)
    * l'utilisation des itemsReader, itemWriter et itemProcessor 

🧪 <font color=red> Exemple simple: </font>

Dans notre classe JobWithChunckedOrientedStep, nous créons une méthode suivante : 


    @Bean
    private Step firstChunkStep() {
        logger.info("👉 step firstChunkStep en cours ... ");
        return stepBuilderFactory.get("First Chunck Step")
        .<Integer, Long>chunk(3)
        .reader(firstItemReader)
        .processor(firstItemProcessor)
        .writer(firstItemWriter)
        .build();
    }

Elle utilise La classe FirstItemReader, FirstItemProcessor et FirstItemWriter comme reader , processor et writer .

🧪 <font color=red>Autre Exemple: </font> création d'un step chunck-oriented traiter un lot de 3 string en entrée et en sortie :


    @Bean
    public Step stepChunked(JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<String> reader,
        ItemProcessor<String, String> processor,
        ItemWriter<String> writer) {
        return new StepBuilder("stepChunked", jobRepository)
            .<String, String>chunk(3, transactionManager) // chunk de 3
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

chunk(3) signifie que Spring lira, traitera, puis écrira par blocs de 3 éléments.

🔹 Composants utilisés

<font color=yellow> LE READER </font>

    @Bean
    public ItemReader<String> reader() {
        return new ListItemReader<>(List.of("alice", "bob", "carol", "dave", "eva"));
    }


<font color=yellow> LE PROCESSOR </font>

    @Bean
    public ItemProcessor<String,String> processor() {
        return item -> item.toUpperCase(); //transforme les noms en majuscules
    }

<font color=yellow> LE WRITER </font>

    @Bean
    public ItemWriter<String> writer () {
        return items -> {
            System.out.printLn("📦 Ecriture d'un chunck :  "+items);
        }
    }

🧾 Console (avec chunk = 3)

        📦 Écriture d’un chunk : [ALICE, BOB, CAROL]
        📦 Écriture d’un chunk : [DAVE, EVA]


🧠 Pourquoi c’est puissant ?

* Gère les transactions par groupe d’éléments

* Rollback automatique en cas d’erreur dans le chunk

* Idéal pour les traitements performants et scalables


📌 À retenir

| Élément         | Rôle                          |
|-----------------|-------------------------------|
| `ItemReader`    | Lit les items un par un       |
| `ItemProcessor` | (Optionnel) transforme l’item |
| `ItemWriter`    | Écrit la **liste** d’items    |
| `chunk(N)`      | Nombre d’éléments par groupe  |
