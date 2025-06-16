# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 13 : Intro au itemWriter dans Spring batch </font>

Un ItemWriter est un composant spring batch chargé d'écrire les objets traités (par le itemProcessor, s'il est utilisé)
vers une destination : fichier, base de données , API , etc...


💡 <font color=red. > Il est utilisé en mode "chunck" </font>

Spring batch lit plusieurs items, les traite, puis les écrit par groupe(chunck), via un itemWriter.

🧱 <font color=red> Interface de base </font>


        public interface ItemWriter<T> {
            void writer(List<? extends T> items) throws Exception;
        }

* items = une liste d'objets prêts à être écrits
* Tu gères l'écriture en lot (par exemple : insertion en base, dans un fichier, etc...)


✅ <font color=red> Comment créer/ajouter des itemWriter dans un projet spring-batch </font>

🥇 À partir d'une classe 

1. créer un package pour ses itemWriter (ici writer)
2. y ajouter la/les classe(s) implémentant l'interface ItemWriter< T>, sans oublier l'annotation @Component
3. Redéfinir la méthode, en fonction du type de write < T >

🥈 À partir d'une méthode dans une classe de configuration 

1. Créer la classe de configuration avec l'annotation @Configuration
2. Créer une méthode du nom du writer avec l'annotation @Bean 




🧪 <font color=red> Exemple 0 : notre classe FirstItemWriter écrit en console la liste des entiers convertie en Long </font>

🧪 <font color=red> Exemple 1 : écrire en console </font>

        @Component
        public class ConsoleWriter implements ItemWriter<String> {
            
            @Override
            public void write (List<? extends String> items) {
                items.forEach(items -> System.out.printLn(" Ecriture : "+item);
            }
        }

🧪 <font color=red> Exemple 2 : écrire dans un fichier </font>

        @Bean 
        public FlatFileItemWriter<String> fileWriter() {
        
            FlatFileWriter<String> writer = new FlatFileWriter<>();
            writer.setResource(new FileSystemResource("outPut.txt"));
            writer.setLineAggregetor(item -> item);
            return writer;
        }

🧪 <font color=red> Exemple 3 : écrire dans une base de données via JDBC </font>

        @Bean
        public JdbcBatchItemWriter<Client> dbWriter( DataSource dataSource) {

            JdbcBatchItemWriter<Client> writer = new JdbcBatchItemWriter<Client>()
                .dataSource(dataSource)
                .beanMapped()
                .build();
            return writer();
        }

🚀 <font color=red> Attention :  </font>

La différence entre l'implémentation d'une classe qui implémente <i> ItemWriter < T > </i> et la methode <i> @Bean </i> 
qui retourne un <i> ItemWriter < T > </i> dépend simplement de la manière dont tu déclares et fournit ce composant à Spring Batch.
Les deux méthodes servent à créer un ItemWriter, mais ...

🔹 1. Implémentation directe d’une classe : 


        @Component
        public class MyConsoleWriter implements ItemWriter<String> {
            @Override
            public void write(List<? extends String> items) {
                items.forEach(System.out::println);
            }
        }

➡️ Ici, tu crées une classe dédiée, annotée avec @Component, et Spring la détecte automatiquement via le scanning des composants.

🔸 Utilisé quand tu veux :

* Du code bien organisé, testable 
* Injecter des dépendances via le constructeur 
* Réutiliser le writer ailleurs


🔹 2. Méthode avec @Bean

        @Bean
        public ItemWriter<String> myWriter() {
            return items -> items.forEach(System.out::println);
        }

➡️ Ici, tu déclares un bean dans une classe de configuration @Configuration. C’est plus rapide et souvent utilisé pour des writers simples ou construits via builder (JdbcBatchItemWriter, FlatFileItemWriter, etc.).

🔸 Utilisé quand tu veux :

* Un writer rapidement défini, sans classe séparée 
* Retourner un writer déjà existant (librairie, builder, etc.)

🎯 Résumé comparatif

| Approche                  | Quand l’utiliser                                |
|---------------------------|-------------------------------------------------|
| **Classe + `@Component`** | Logique métier spécifique, test unitaire facile |
| **Méthode `@Bean`**       | Writer standard ou construit avec un builder    |



En pratique
Les deux sont 100% compatibles avec Spring Batch. Le choix dépend surtout :

* De la complexité du writer

* De ta préférence en organisation du code

* Du besoin de configuration dynamique (plus facile dans une méthode @Bean)






🧠 <font color=red> Resumé </font>

| Élément         | Rôle                              |
|-----------------|-----------------------------------|
| `ItemReader`    | Lire des objets                   |
| `ItemProcessor` | Transformer / filtrer les objets  |
| `ItemWriter`    | Écrire une liste d’objets traités |
