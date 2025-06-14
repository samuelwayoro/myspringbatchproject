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

<b> Exemple : implémentation des Tâches secondTask et thirdTask </b>.