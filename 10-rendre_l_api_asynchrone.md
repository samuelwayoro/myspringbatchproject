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
