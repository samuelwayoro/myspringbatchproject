# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 14 : Lancer votre job spring batch manuellement avec un REST API ou avec un scheduler  </font>

🧠 <font color=red> Contexte </font> 

Par défaut, Spring-boot exécute automatiquement tous les jobs batch déclarés comme @Bean lors du démarrage de l'application.

Cela signifie : 

        @Bean 
        public Job myJob(...){...}

Sera exécuté dès que l'application se lance.

✅ <font color=red> Comment empêcher cela ?</font>

En ajoutant la commande suivante dans le fichier : application.properties 

        spring.batch.job.enabled=false

✅ <font color=red>Résultat : </font>

Elle désactive ce comportement automatique :

- Le(s) job(s) ne s'exécute pas au démarrage 
- tu peux le lancer manuellement via :

  * Un contrôleur REST 
  * une invocation depuis la main()
  * une interface utilisateur 
  * un JobLauncher

🧾 <font color=red> En résumé </font>

| Propriété                                  | Effet                                     |
|--------------------------------------------|-------------------------------------------|
| `spring.batch.job.enabled=true` *(défaut)* | Les jobs sont exécutés automatiquement    |
| `spring.batch.job.enabled=false`           | Les jobs doivent être lancés manuellement |

