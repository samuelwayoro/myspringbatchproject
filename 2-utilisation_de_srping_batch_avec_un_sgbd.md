
# <font color=green> myspringbatchproject 🎯 </font>


<b>

#### 🔥 <font color=red> ATTENTION : Il est obligatoire de toujours démarrer son projet spring batch avec une dépendance à une base de données (même mémoire comme h2 par défaut) au risque d'avoir une exception lors de l'éxécution du projet. Son rôle est de gérer l'état des traitements batch , sauvegarder les métadonnées d'exécution : job lancés , étapes terminées, tentatives, erreurs, redémarrages, etc.

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


🚀 <font color=red> Explication : </font> Ordre d'enregistrement dans les tables Spring Batch 

Lorsque tu exécutes un job, voici l'ordre des insertions dans la base : 


🔹 1. BATCH_JOB_INSTANCE :

- Créée une seule fois par combinaison (job_name, job_parameters)
- Sert d'identifiant logique du job
- Contient job_instance_id, job_name, job_key
- 📝 <font color=red> Insert uniquement si le même job avec les mêmes paramètres n’a jamais été lancé.</font>


🔹 2. BATCH_JOB_EXECUTION :

- Insérée à chaque lancement réel (même si l'instance existe déjà)
- Représente une exécution spécifique du job
- Contient job_execution_id, start_time, status, etc.
- 📝 Reliée à job_instance_id

🔹 3. BATCH_JOB_EXECUTION_PARAMS : 

- Stocke les paramètres passés à cette exécution
- Un paramètre = une ligne
- 📝 Reliée à job_execution_id


🔹 4. BATCH_JOB_EXECUTION_CONTEXT : 

- Contexte global (Map<String, Object>) sérialisé en JSON ou binaire
- 📝 Souvent rempli en fin de job ou par un listener


🔹 5. BATCH_STEP_EXECUTION (pour chaque step) : 
- Une ligne par step exécuté
- Ajoutée quand un step démarre
- 📝 Reliée à job_execution_id

🔹 6. BATCH_STEP_EXECUTION_CONTEXT: 

- Comme pour le job, stocke le contexte sérialisé d’un step


📌 Schéma d’ordre simplifié : 

        1. BATCH_JOB_INSTANCE             ← si nécessaire
        2. BATCH_JOB_EXECUTION
        3. BATCH_JOB_EXECUTION_PARAMS
        4. BATCH_STEP_EXECUTION (xN)
        5. BATCH_STEP_EXECUTION_CONTEXT (xN)
        6. BATCH_JOB_EXECUTION_CONTEXT



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

Comme expliqué un projet avec Spring Batch ne peut se faire sans un SGBD.
Dans le cadre de ce projet, nous utiliserons MariaDB, pour nos tests.
Nous procédons alors au remplacement de la dépendance maven de H2 à MariaDB, et configurons l'accès à notre base de
données nommée spring_batch, dans le fichier applications.properties.

L'utilisation d'un sgbd à la place de la base de données mémoire h2, nous permet de bien observer le cycle de vie d'une
instance de Job et de ses steps.

En effet, les tables suivantes sont créées :


| Table                          | Rôle                                                                      |
|--------------------------------|---------------------------------------------------------------------------|
| `BATCH_JOB_INSTANCE`           | Identifie chaque instance logique d’un job                                |
| `BATCH_JOB_EXECUTION`          | Enregistre chaque exécution d’un job                                      |
| `BATCH_JOB_EXECUTION_PARAMS`   | Stocke les paramètres d’un job (sous forme clé/valeur)                    |
| `BATCH_STEP_EXECUTION`         | Enregistre chaque exécution d’un step                                     |
| `BATCH_STEP_EXECUTION_CONTEXT` | Contexte d’exécution d’un step (stocké sous forme de hash map sérialisée) |
| `BATCH_JOB_EXECUTION_CONTEXT`  | Contexte global d’un job                                                  |

Et à chaque exécution de projet Spring batch, on peut s'apercevoir que :

<font color=red> l'exécution d'une instance de notre projet entraîne l'enregistrement des informations de notre du Job, 
  de Steps, de leurs paramètres et de leur contexts dans les tables précités. 
  Si le job est exécuté avec succès, sa réexécution 
  n'est pas possible et affichera un message d'alert dans les logs de spring batch. 
  De plus ce ne sont que les tables : BATCH_JOB_EXECUTION et BATCH_JOB_EXECUTION_CONTEXT qui auront de nouvelles lignes.
  Toutes les autres tables n'auront aucune nouvelles.

🔥 Ce qui veut dire qu'une seule instance d'un JOB n'est exécutable. 


---

### 📚 <font color=green> étape 8 : Exécution d'un même JOB sous plusieurs instances </font>

Dans un projet spring batch, les JOB sont stockés dans la table BATCH_JOB_INSTANCE avec un id chacun.
De ce fait un même JOB ne peut être réexécuté. 
Afin d'exécuter un même JOB plusieurs fois, il faudrait le paramétrer au lancement de l'application :

- en passant le(s) paramètres : <font color=yellow>run=nomParametre</font> au lancement via l'éditeur de développement 
- ou en ligne de commande 

Ceci entrainera une nouvelle instance du même JOB à chaque lancement à cause des arguments.

---
### 📚 <font color=green> étape 9 : Exécution d'un même JOB sous plusieurs instances avec paramètre incrémentés </font>

Afin de ne pas saisir à chaque fois les paramètres aux lancements de notre projet de Spring batch; il est possible de les automatiser
et les rendre incrémentable. 

Le framework Spring batch offre une méthode <font color=red> .incrementer(new RunIncrementer()) </font> lors de la création du Job permettant de le faire:

    @Bean
    public Job firstJob() {
        return jobBuilderFactory
                .get("First Job")
                .incrementer(new RunIdIncrementer())  
                .start(firstStep())
                .next(secondStep())
                .next(thirdStep())
                .build();
    }



<font color=red> Celle-ci rajoutera automatiquement un paramètre du nom de run.id à l'instance du job qui sera exécuté (en plus des autres paramètres s'il y en a)
et seront stockés dans la table : batch_job_execution_params </font> avec pour première valeur 1.

🎯<font color=red>NB </font> : La vérification de la valeur du paramètre run.id est visible dans la table "batch_job_execution_params".  

</b>