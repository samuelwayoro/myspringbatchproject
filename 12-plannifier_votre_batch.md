# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 18 : Plannifier votre batch</font>

<font color=red> 🧭 Pourquoi utiliser un scheduler avec Spring Batch ? </font>

Utiliser un planificateur (scheduler) avec un job Spring Batch permet d’automatiser son exécution à des intervalles réguliers, sans intervention manuelle.

| Objectif                                   | Exemple                                                 |
|--------------------------------------------|---------------------------------------------------------|
| 🔁 Exécuter un job à intervalles réguliers | Lancer un traitement toutes les nuits à 2h              |
| 🕐 Gérer des flux périodiques              | Collecte quotidienne de fichiers, reporting mensuel     |
| 🧑‍💻 Éviter le déclenchement manuel       | Plus besoin d’appeler manuellement une API ou interface |
| 🧪 Automatiser les tests réguliers         | Job de contrôle qualité, purge, archivage               |


⚙️ <font color=red> Comment planifier un job Spring Batch ?</font>

Spring Boot permet d’utiliser le scheduler intégré via @Scheduled, ou un outil externe (Quartz, cron, etc.).

✅ Méthode simple avec @Scheduled

1. Activer le scheduling dans la classe principale :

    
    @SpringBootApplication
    @EnableScheduling
    public class BatchApp { ... }


2. Créer un service planifié : <font color=red> et annoter (@Scheduled) sa méthode qui lance le job sans oublier le script cron </font>


    @Service
    public class ScheduledJobLauncher {

        @Autowired
        private JobLauncher jobLauncher;
    
        @Autowired
        private Job myJob;
    
        @Scheduled(cron = "0 0 2 * * ?") // tous les jours à 2h du matin
        public void runJob() throws Exception {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // unicité
                    .toJobParameters();
    
            jobLauncher.run(myJob, params);
        }
    }



⏱️ <font color=red> Syntaxe cron </font>

<i> Format  : second minute hour day month dayOfWeek</i>

Exemple : 

* "0 0 * * * *" → toutes les heures
* "0 30 2 * * ?" → tous les jours à 2h30
* "0 0 0 1 * ?" → chaque 1er du mois à minuit


🧱 Alternatives plus avancées

| Outil                                      | Avantage principal                         |
|--------------------------------------------|--------------------------------------------|
| **Quartz**                                 | Jobs complexes, déclenchements persistants |
| **Spring Cloud Tasks**                     | Pour le cloud, traitements éphémères       |
| **Externe (Airflow, Jenkins, Cron Linux)** | Intégration dans pipelines CI/CD           |



✅ Résumé

| Élément             | But                                                 |
|---------------------|-----------------------------------------------------|
| `@EnableScheduling` | Active le planificateur intégré de Spring Boot      |
| `@Scheduled(...)`   | Définit la fréquence d’exécution                    |
| `JobLauncher`       | Lance le job manuellement dans la méthode planifiée |
| `JobParameters`     | Doivent être **uniques à chaque exécution**         |


Dans notre exemple, nous planifions le job "Second job" en réécrivant un service planifié (SecondJobScheduler) qui se lancera chaque minute.
En somme pour rendre un job planifié, il faut rendre son service planifié.