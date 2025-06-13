# <font color=green> myspringbatchproject 🎯 </font>


### 📚 <font color=green> étape 1 : CREATION DU PROJET </font>
Création du projet avec Spring initializer 
avec les dépendances réquises : 

- spring batch 
- spring-boot comme projet parent 
- une instance de bd : ici H2 (base de données mémoire)

### 📚 <font color=green> étape 2 : INITIALISATION DU PROJET BATCH </font>

Il s'agit de configurer le projet, en ajoutant l'annotation @EnableBatchProcessing
dans la classe Main du projet. 

### 📚 <font color=green> étape 3 : AJOUT DE LA CONFIGURATION DU PROJET </font>

Créer un nouveau package de préférence nommé "config". 
Y ajouter une nouvelle (ici SampleJob) classe annotée : <font color=red> @Configuration</font>
qui permettra de configurer le(s) Job(s) et ses Step(s).


### 📚 <font color=green> étape 4 : AJOUT DE LA CONFIGURATION DU PROJET </font>

Créer et configurer les Jobs et ses steps dans la classe créée dans le package config.

<font color=red> NB : </font> S'assurer d'injecter sous @Autowired les bon objets 
JobBuilderFactory et StepBuilderFactory dans la classe du package config (ici SampleJob).