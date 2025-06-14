# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 10 : Utilisation de "Listner" dans un projet spring batch </font>

Un listener dans Spring Batch sert à réagir à des événements pendant l'exécution d’un job ou d’un step (comme le début, la fin, une erreur, etc.).

Il permet d’accrocher du code personnalisé à des moments-clés du traitement batch (avant/après job ou step, en cas d’erreur, etc.).


✅ <font color=red> À quoi ça sert concrètement ?</font>

* À exécuter du code avant ou après un job ou un step (log, audit, envoi de mail, mesures, etc.).
* À intercepter ou ajouter des valeurs dans le JOB à travers les Steps (via le code d'une des task).

📌 <font color=red> Exemple simple : </font>

Un JobExecutionListener qui affiche un message avant et après le job.

        @Component
        public class JobLoggerListener implements JobExecutionListener {
    
        @Override
        public void beforeJob(JobExecution jobExecution) {
            System.out.println("🔄 Le job commence...");
        }
    
        @Override
        public void afterJob(JobExecution jobExecution) {
            System.out.println("✅ Le job est terminé !");
        }
    }

Dans notre projet, nous avons ajouté un Listner "FirstListner" à notre Job "FirstJob".


🧠 <font color=red> Comment rajouter un Listner dans un projet spring batch </font>

1. Créer un package pour les listner du projet (listner package), et le rajouter dans le @ComponantScan de la classe Main 


2. Y créer les classes de listner (de préférence à partir du nom de leur Job respectif) ; les faire implémenter l'interface <font color=red> "JobExecutionListener"</font> et <font color=red>@Component</font>.
Cette implémentation donne l'opportunité de redéfinir les méthodes <font color=red>beforeJob()</font> qui se lance avant l'exécution du Job, et la méthode <font color=red>afterJob()</font>,
qui elle s'exécute à la fin du Job. 
Il est possible, d'exécuter de la logique dans ses méthodes (code métier, logs ou autres ...). Dans notre exemple, nous écrivons des traces logs, et rajoutons une variable (Map : clé-valeur) dans le context du Job : <font color=yellow> <i> .jobExecution.getExecutionContext().put("Nom", "WAYORO");</i></font>

<font color =red>NB : </font> Toutes variables rajoutées dans le contexts via le Listner est utilisables dans tous les Step du Job. Dans notre exemple la map {"nom","WAYORO"} est utilisée dans le step SecondStep à travers sa Task SecondTask.


📚 <font color=red> Types de listeners disponibles : </font>

* JobExecutionListener → pour tout le job

* StepExecutionListener → pour chaque step

* ItemReadListener, ItemWriteListener, etc. → pour surveiller les opérations item par item