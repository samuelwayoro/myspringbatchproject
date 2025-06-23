# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 19 : Stopper un job via son API REST</font>

<font color=red> 🧭 Arrêter un job Spring Batch via une API REST consiste à : </font>

1. Identifier le job en cours par son ID (via les logs ou la base de données dans la table batch_job_execution)
2. Envoyer une requête REST pour demander son arrêt 
3. Utiliser L'API de spring batch pour stopper proprement le job

✅ <font color=red> Étapes à suivre</font>

1. Exposer un endpoint DELETE /stop-job/{executionId}
2. Injecter et utiliser un objet de type JobOperator pour arrêter le job depuis le code service.

🎯 <font color=red> Ce que fait jobOperator.stop(id) : </font>

* Envoie un signal d'arrêt au job (pas immédiat)
* le job doit vérifier périodiquement si a été stoppé, notamment dans un Reader, Processor, ou un Writer.
* S'il est bien conçu, il se termine proprement.


🛑 <font color=red> Important : </font> conditions pour que l’arrêt fonctionne

- Le job doit être en cours d’exécution (status STARTED)
- Le thread doit honorer la demande d’arrêt (sinon il reste bloqué)
- Le JobOperator doit avoir été correctement configuré


<font color=red> 🧪 Test API </font>

1.Lancer un job : 

    POST /run-job
    # -> ID = 17

2.Stoppe le job : 

    DELETE /jobs/stop/17

📌 <font color=red> Pour aller plus loin :</font> interruption "gracieuse"

Dans le Reader ou Processor, on peut faire ceci :

    StepExecution stepExecution = StepSynchronizationManager.getContext().getStepExecution();

    if (stepExecution.isTerminateOnly()) {
        throw new JobInterruptedException("Interruption demandée.");
    }

Cela permet au job de s’arrêter proprement si une interruption a été demandée.
