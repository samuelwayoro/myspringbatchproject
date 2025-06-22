# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 15 : Gérer votre job spring batch manuellement avec un REST API  </font>

🧠 <font color=red> Contexte </font>

La gestion manuelle d'un job Spring batch via une API REST consiste à exposer un point d'entrée HTTP (souvent via un @RestController)
qui permet de déclencher un job batch à la demande, en lui passant éventuellement des paramètres dynamiques.


✅ <font color=red> Pourquoi faire cela ?</font>

* 🔁 relancer un job à la demande 
* 🕹️ Contrôle externe via un front-end ou un script 
* ⚙️ Fournir des paramètres personnalisés (dates, fichiers, etc...)
* 📊 Intégrer un batch dans un processus métier plus large

<font color=red>⚙️ Composants impliqués : </font>

| Élément           | Rôle                                            |
|-------------------|-------------------------------------------------|
| `JobLauncher`     | Composant Spring Batch qui lance un job         |
| `Job`             | Le job à exécuter                               |
| `JobParameters`   | Paramètres à passer au job (identifiants, etc.) |
| `@RestController` | Expose l'API REST                               |


🧾 <font color=red> Etapes à suivre : </font>

1. S'assurer que l'instruction suivante est ajouté dans le application.properties : <font color=yellow> spring.batch.job.enabled=false </font>


2. Créer un package "controller" pour le(s) controller(s)


3. Rajouter la dépendance (Maven ou Gradel) spring-boot-stater-web au projet


4. Configurer le job (implémentation du job dans la classe @Configuration)


5. Développer le REST Controller, sans oublier : 

    * Injecter les job(s) à lancer (en utilisant @Qualifier afin de les différencier) 
    * injecter un JobLauncher 
    * Le mécanisme de JobParameters unique dans la récupération des noms des jobs


6. Appeler le REST API mis à disposition à partir d'un outil (Postman ou autre...) à partir de son url (endpoint)

<font color=red> 📌 À retenir: </font>

* le job ne se lance plus automatiquement ➜ spring.batch.job.enabled=false

* Tu contrôles le moment et les paramètres 

* Tu peux même relancer un même job plusieurs fois (si les JobParameters sont uniques)

* Tu peux gérer les retours : succès, échec, logs...