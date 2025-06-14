# <font color=green> myspringbatchproject 🎯 </font>


### 📚 <font color=green> étape 1 : Création d'un projet spring batch </font>
<B>
Création du projet avec Spring initializer 
avec les dépendances réquises : 

- spring batch 
- spring-boot comme projet parent 
- une instance de bd : ici H2 (base de données mémoire)

----

### 📚 <font color=green> étape 2 : Initialisation du projet </font>

Il s'agit de configurer le projet, en ajoutant l'annotation @EnableBatchProcessing
dans la classe Main du projet. 

----

### 📚 <font color=green> étape 3 : Création de(s) la classe(s) de configuration du projet</font>

Créer un nouveau package de préférence nommé "config". 
Y ajouter une nouvelle (ici SampleJob) classe annotée : <font color=red> @Configuration</font>
qui permettra de configurer le(s) Job(s) et ses Step(s).

---

### 📚 <font color=green> étape 4 : Ajout de la configuration du projet</font>

Créer et configurer les Jobs et ses steps dans la classe créée dans le package config.

1 - Injecter sous @Autowired les propriétés private : JobBuilderFactory et StepBuilderFactory dans 
la classe du package config (ici SampleJob), pour l'instanciation des Jobs et leurs Steps.

2- implémenter leurs Tâches ("Taskes") qui peuvent être de type Tasklet ou Chunked-Oriented, en fonction du besoin.

---

### 📚 <font color=green> étape 5 : Implémentation des Tâches (Tasks) dans des classes de service </font>

Dans un souci de "clean code" il est conseillé de coder la logique de l'implémentation des "Tasks" dans des classes
d'un autre package nommé couramment "service".
Pour coder ses Tasks dans une classe dédiée, suivre les étapes suivantes : 

1- Créer la classe portant le nom de la "Task" dans le package nommé "service" et lui ajouter l'annotation @Service.

2- ajouter ce nouveau package (service) dans le componentScan de la classe Main du projet (ici la classe MyspringbatchprojectApplication)

3- Lui faire implémenter l'interface <font color=red> Tasklet </font>

4- Redéfinir la méthode <font color=red> execute() </font> de cette interface  

5- Injecter (@Autowired sous propriété private) cette / ces classe(s) dans la classe de définition des Job (ici SampleJob)

<b> Exemple : implémentation des Tâches secondTask et thirdTask

---

#### 🔥 <font color=red> ATTENTION : il est obligatoire de toujours démarrer son projet spring batch avec une dépendance à une base de données (même mémoire comme h2 si dans la mesure du possible) au risque d'avoir une exception lors de l'éxécution du projet. Son rôle est de gérer l'état des traitements batch , sauvegarder les métadonnées d'exécution : job lancés , étapes terminées, tentatives, erreurs, redémarrages, etc.
Elle est indispensable parce que : 
- elle permet de reprendre un job là oû, il s'est arrêté (redémarrage) 
- évite de relancer un job déjà exécuté (gestion des identifiants d'exécution)  
- stocke les logs de traitements (dans les tables BATCH_JOB_EXECUTION, etc.)

Sans base de données : 

- Impossible de suivre l'état des jobs 
- pas de reprise possible 
- pas de fiabilité en production

🛑 NB : Spring batch peut utiliser une bdd en mémoire (tel H2) pour des tests ou prototypes, mais pas en prod.
</font>

---

### 📚 <font color=green> étape 6 : Comprendre comment les méta données des Job et de leurs steps sont stockées dans le SGBD utilisé </font>

En Spring Batch, les métadonnées des jobs et des steps sont stockées dans des tables relationnelles dans le SGBD configuré, pas en tant que paires clé-valeur génériques, mais plutôt dans une structure SQL normalisée.

✅ <font color=orange> Comment les métadonnées sont stockées ? </font>

Spring Batch crée automatiquement un ensemble de tables spécifiques pour suivre l’état des jobs. Ces tables sont relationnelles (pas des paires clé/valeur au sens strict) et reliées entre elles par des clés primaires/étrangères.

📂 <font color=orange >Tables principales créées par Spring Batch </font>

Voici les tables principales utilisées pour stocker les métadonnées :

| Table                          | Rôle                                                                      |
|--------------------------------|---------------------------------------------------------------------------|
| `BATCH_JOB_INSTANCE`           | Identifie chaque instance logique d’un job                                |
| `BATCH_JOB_EXECUTION`          | Enregistre chaque exécution d’un job                                      |
| `BATCH_JOB_EXECUTION_PARAMS`   | Stocke les paramètres d’un job (sous forme clé/valeur)                    |
| `BATCH_STEP_EXECUTION`         | Enregistre chaque exécution d’un step                                     |
| `BATCH_STEP_EXECUTION_CONTEXT` | Contexte d’exécution d’un step (stocké sous forme de hash map sérialisée) |
| `BATCH_JOB_EXECUTION_CONTEXT`  | Contexte global d’un job                                                  |


🔑 <font color=orange> Ce qui est effectivement stocké en clé/valeur : </font>

Les contextes d’exécution (ExecutionContext) sont stockés dans :

BATCH_JOB_EXECUTION_CONTEXT

BATCH_STEP_EXECUTION_CONTEXT

Ces champs contiennent une sérialisation d'une Map<String, Object> (typiquement une chaîne JSON ou un blob encodé) — c’est là qu'on trouve des paires clé/valeur.

🧠 <font color=orange>Exemple concret</font>
Tu exécutes un job ImportClientJob avec idClient=42.

Les données seront enregistrées comme suit :

BATCH_JOB_INSTANCE : une ligne pour ImportClientJob

BATCH_JOB_EXECUTION : une ligne pour l’exécution actuelle

BATCH_JOB_EXECUTION_PARAMS : clé = idClient, valeur = 42

BATCH_STEP_EXECUTION : une ligne pour chaque step exécuté

BATCH_STEP_EXECUTION_CONTEXT : des infos internes, comme read.count, commit.count, etc.

🧪 <font color=orange>Comment sont créées ces tables ?</font>

Spring Batch fournit un script SQL (schema-*.sql) adapté à chaque SGBD (MySQL, PostgreSQL, H2, etc.) dans le package :

    org.springframework.batch.core.schema

Tu peux l’exécuter manuellement ou le laisser Spring Boot le faire automatiquement si spring.batch.initialize-schema=always est activé.

📌 <font color=orange>En résumé</font>

Les métadonnées des jobs sont stockées dans une base relationnelle via des tables bien définies.

Les paramètres et contextes d'exécution sont les seuls stockés en paires clé/valeur sérialisées.

Cette structure permet la reprise, le suivi et l’audit des traitements batch.

---

### 📚 <font color=green> étape 7 : Configuration et utilisation de Mariadb à la place de H2</font>

Comme expliqué un projet avec sSpring Batch ne peut se faire sans un SGBD. 
Dans le cadre de ce projet, nous utiliserons MariaDB, pour nos tests. 
Nous procédons alors au remplacement de la dépendance maven de H2 à MariaDB, et configurons l'accès à notre base de 
données nommée spring_batch, dans le fichier applications.properties.

L'utilisation d'un sgbd à la place de la base de données mémoire h2, nous permet de bien observer le cycle de vie d'une 
instance de Job et de ses steps. 

En effet, les tables suivantes sont créé :


| Table                          | Rôle                                                                      |
|--------------------------------|---------------------------------------------------------------------------|
| `BATCH_JOB_INSTANCE`           | Identifie chaque instance logique d’un job                                |
| `BATCH_JOB_EXECUTION`          | Enregistre chaque exécution d’un job                                      |
| `BATCH_JOB_EXECUTION_PARAMS`   | Stocke les paramètres d’un job (sous forme clé/valeur)                    |
| `BATCH_STEP_EXECUTION`         | Enregistre chaque exécution d’un step                                     |
| `BATCH_STEP_EXECUTION_CONTEXT` | Contexte d’exécution d’un step (stocké sous forme de hash map sérialisée) |
| `BATCH_JOB_EXECUTION_CONTEXT`  | Contexte global d’un job                                                  |

Et à chaque exécution de projet Spring batch, on peux s'appercevoir que : 

- <font color=red> l'exécution d'une instance de notre projet entraîne l'enregistrement des informations de notre du Job, 
  de Steps, de leurs paramètres et de leur contexts dans les tables précités. Si le job est exécuté avec succès, sa réexécution 
  n'est pas possible et affichera un message d'alert dans les logs de spring batch. 
  De plus ce ne sont que les tables : BATCH_JOB_JOB_EXECUTION et BATCH_JOB_EXECUTION_CONTEXT qui auront de nouvelles lignes.
  Toutes les autres tables n'auront aucune nouvelles.
  

- Cependant, lorsque le projet Spring est exécuté  