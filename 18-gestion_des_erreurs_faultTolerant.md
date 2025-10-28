# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> Etape 24 : La gestion des erreurs (exceptions) dans un projet batch : .faultTolerant() </font>


La méthode ".faultTolerant()" joue un rôle important dans la gestion des erreurs dans un Step Spring en activant le mode 
tolérant aux fautes ("fault-tolerant mode") pour un step chunck-oriented.

En d'autres termes, elle permet au Step de continuer le traitement malgré certaines erreurs, au lieu d'arrêter brutalement 
le job à la prémière exception.


⚙  <font color=yellow>Sans .faultTolerant </font>

Par defaut, si une exception survient : 

* Le Step s'arrête immédiatement 
* Le job échoue
* rien n'est "skippé", retraité ou réessayé 

👉 comportement strict, pas tolérant.

⚙ <font color =yellow>Avec .faultTolerant</font>

Spring batch t'autorise à définir les règles de tolérance aux erreurs, telles que : 

- <font color=red> .skip(Exception.class) : </font> Ignorer certains types d'erreurs 
- <font color=red> .skipLimit(n) : </font> Nombre max d'éléments à ignorer 
- <font color=red> .retry(Exception.class) :  </font> Réessayer un élément en cas d'erreur temporaire
- <font color=red> .retryLimit(n) : </font> Nombre max de tentatives de retry 
- <font color=red> .skipPolicy() : </font> Politique personnalisée pour décider quoi ignorer 
- <font color=red> .noRollback(Exception.class) : </font> ne pas faire de rollback sur certaines erreurs

📖 <font color=yellow> Exemple concret : </font>

    @Bean
    public Step importStep(JobRepository repo, PlatformTransactionManager txManager) {
        return new StepBuilder("importStep", repo)
        .<Personne, Personne>chunk(10, txManager)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .faultTolerant()                        // <-- ici
        .skip(FlatFileParseException.class)     // ignore erreurs de parsing
        .skipLimit(5)                           // max 5 erreurs tolérées
        .retry(SQLException.class)              // retente si erreur SQL
        .retryLimit(3)                          // max 3 tentatives
        .build();
    }


👉 Résultat : 

- Si une ligne du fichier input est corrompue, elle est ignorée (skip)
- Si la base est momentanement indisponible, Spring retente (retry)
- le Job continue tant que les erreurs restent dans les limites 

🎯 <font color = red>En bref </font>

| Sans `.faultTolerant()`            | Avec `.faultTolerant()`             |
| ---------------------------------- | ----------------------------------- |
| Le job s’arrête à la 1ère erreur   | Le job peut continuer               |
| Aucun élément n’est sauté          | On peut ignorer certaines erreurs   |
| Aucun retry possible               | On peut réessayer un item           |
| Pas de contrôle fin des exceptions | Contrôle précis des fautes tolérées |

Avec .faultTolerant() = “Je veux que mon batch soit robuste” 💪
C’est le point d’entrée obligatoire pour activer toutes les stratégies d’erreurs :

.skip()
.retry()
.skipPolicy()

... etc.