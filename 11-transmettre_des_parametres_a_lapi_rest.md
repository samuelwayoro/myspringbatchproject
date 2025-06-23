# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 17 : Transmettre des paramètres (job paramètres) à L'API REST </font>

🧠 <font color=red> Pourquoi passer des paramètres à un job ? </font>

1. ✅ Rendre le job réutilisable : 

Un même job peut être utilisé avec différents contexte d'exécution :

➡️ Exemple : importer un fichier spécifique

         POST /run-job?filename=data-june.csv

2. 📅 Passer une date, période ou identifiant métier : 

Pour traiter des données liées à une période précise :


        POST /run-job?date=2025-06-01

➡️ Le job pourra filtrer les données en base avec ce critère.


3. 🔄 Rendre le job redémarrage : 

Spring Batch n’exécute pas deux fois le même job avec les mêmes paramètres, donc :

Si tu ne passes pas de paramètre unique (ex: run.id ou timestamp), Spring Batch dira :
❌ "Job instance already completed and cannot be restarted".

4. 🔐 Transmettre des infos sécurisées ou techniques : 

Tu peux passer :

- un token 
- un nom d’utilisateur 
- un chemin de fichier 
- un flag d’environnement (test, prod)

5. ⚙️ Piloter le job depuis d'autres services : 

Une API avec paramètres permet :

- de déclencher un job depuis une UI d’administration 
- de le connecter à un système externe (cron, webhook, etc.)


📑 <font color=red> Comment passer des paramètres à notre API ? </font>

Tout dépend de la nature des paramètres (chaîne de caractère, fichier, etc...).
Dans notre projet exemple, nous passons des paramètres json au endpoint de notre api, dans l'objectif de l'utiliser à 
travers du job.

1- <font color =yellow. > Nous créons un package contenant des bean :</font> 
Dans la majorité des cas les paramètres passée à un endpoint doivent être sérializé (json -> java bean) afin d'être utilisé
dans le job. Dans ce cas, il faudrait créer des classes représentant ces objets jsons.

2- <font color=yellow> Mettre à jour le endpoint en fonction des paramètres à passer : </font>
Dans notre exemple, nous mettons à jour notre endpoint(@GetMapping("/start/{jobName}")) de JobController afin de recevoir 
une liste : @RequestBody List<JobParamsRequest> jobParamRequestList.


3-<font color=yellow> Utilisation des paramètres dans le context : </font>   
Il s'agit tout simplement de rajouter ses paramètres dans la map des paramètres du context. 
Dans notre exemple, nous le faisons dans le service metier (la classe metier @Service), en rajoutant la liste des paramètres:
<font color=red>List<JobParamsRequest> jobParamRequestList</font> dans la map <font color=red> Map<String, JobParameter> params</font>.
