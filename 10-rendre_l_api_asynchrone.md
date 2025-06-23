# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 16 : Rendre l'API REST qui lance les jobs , asynchrone.</font>

🧠 <font color=red> Contexte </font>

Il est tout à fait possible de rendre le lancement d'un job Spring Batch asynchrone via une API REST.

✅ <font color=red> Pourquoi faire cela ?</font>

🛑 Sans asynchronisme 

- L'API attend la fin du job pour répondre (peut durer des minutes)
- Risque de Timeout HTTP
- Moins de scalabilité 

🚀 Avec asynchronisme

- L'API répond immédiatement (exemple : le message "job started" dans notre code)
- le job s'exécute en tâche de fond
- Tu peux exposer un autre endpoint pour suivre son état


🧾 <font color=red> Etapes à suivre : </font>

1. Ajouter l'annotation <font color=red> @EnableAsync </font> à la classe principale du projet Spring Boot 
2. S'assurer d'avoir une classe de service reliée à la classe controller (chaque classe service à sa classe controller si plusieurs) 
contenant la logique de chaque endpoint du controller. Cette classe doit  : 
   - être annotée @Service 
   - avoir une méthode retournant void contenant la logique du endpoint de lancement des job, elle même annotée @Async
   - Cette classe étant un @Service, doit être obligatoirement injectée dans la classe de @Controller afin d'être utilisée à partir de sa méthode. 





<font color=red> 📌 À retenir: </font>

Grâce à l'asynchronisme ajoutée au service de notre API nous pouvons obtenir avoir un traitement sous plusieurs threads différents 
des exécutions de notre batch.

---

🧪 <font color=red> EXEMPLE : </font> 

Voici comment créer une API REST complète pour :

- Lancer un job Spring Batch de façon asynchrone

- Consulter l’état d’un job en cours ou terminé


🧱 Architecture globale

| API                    | Fonction                                  |
|------------------------|-------------------------------------------|
| `POST /run-job`        | Lance un job Spring Batch en arrière-plan |
| `GET /job-status/{id}` | Retourne l'état d’un job par son ID       |


✅ Étapes de mise en œuvre

1. Activer l’exécution asynchrone


      @SpringBootApplication
      @EnableAsync
      public class BatchApplication {
         public static void main(String[] args) {
            SpringApplication.run(BatchApplication.class, args);
         }
      }

2. Service Batch (asynchrone)


      @Service
      public class JobService {
   
         @Autowired
         private JobLauncher jobLauncher;
   
         @Autowired
         private Job job;
   
         @Async
         public void lancerJobAsync(JobParameters params) {
            try {
               JobExecution execution = jobLauncher.run(job, params);
               System.out.println("Job lancé avec ID: " + execution.getId());
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
   
         public JobExecution getJobExecution(Long id) throws NoSuchJobExecutionException {
            return new SimpleJobExplorer(
               new MapJobInstanceDao(), new MapJobExecutionDao(),
               new MapStepExecutionDao(), new MapExecutionContextDao()
            ).getJobExecution(id);
         }
      }

💡 Remarque : en prod, on utiliserait JobExplorer avec une base réelle, pas les DAOs en mémoire.

3. Contrôleur REST


      @RestController
      public class JobController {
   
         @Autowired
         private JobService jobService;
      
         @Autowired
         private JobLauncher jobLauncher;
      
         @Autowired
         private Job job;
   
      @PostMapping("/run-job")
      public ResponseEntity<String> runJob() {
            JobParameters params = new JobParametersBuilder()
               .addLong("time", System.currentTimeMillis())
               .toJobParameters();
   
           try {
               JobExecution exec = jobLauncher.run(job, params);
               return ResponseEntity.ok("Job lancé ! ID = " + exec.getId());
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body("Erreur lors du lancement du job : " + e.getMessage());
           }
      }
   
      @GetMapping("/job-status/{id}")
      public ResponseEntity<String> getStatus(@PathVariable Long id) {
            try {
               JobExecution execution = jobService.getJobExecution(id);
               if (execution == null) {
                  return ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body("Job ID introuvable.");
            }
            return ResponseEntity.ok("Status : " + execution.getStatus());
         } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body("Erreur lors de la récupération du job : " + e.getMessage());
            }
         }
      }

▶ Utilisation


1. Lance le job :


      curl -X POST http://localhost:8080/run-job
      # Réponse : Job lancé ! ID = 7


2. Vérifie son état :

   
      curl http://localhost:8080/job-status/7
      # Réponse : Status : STARTED (ou COMPLETED, FAILED…)


✅ Résultat
Tu as maintenant un système complet :

* Async, non bloquant
* Traçable via l’ID du job
* Utilisable depuis un front-end, un script ou une interface admin
