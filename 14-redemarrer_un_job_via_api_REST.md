# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 20 : Redémarrer un job via son API REST</font>

<font color=red> 🎯 Objectif : </font>
Permettre aux utilisateurs de redémarrer un job en échec via une requête : POST ou GET

    POST /jobs/restart/{executionId}


✅ <font color=red> Étapes à suivre</font>

1. exposer un endpoint pour le redémarrage du job à partir de son id (jobExecutionID)
2. Utiliser l'objet <font color=red> JobOperator </font> (injecté dans la couche service) qui prendra en paramètre l'id du job afin d'interagir 
avec celui-ci. 

<font color=red>🛑 Condition nécessaire pour redémarrer un job </font> 

- Le job doit être terminé en échec <font color=red> (BatchStatus.FAILED) </font> 
- Le job doît être redémarrable <font color=red> (par défaut, c'est le cas sauf si restartable=false) </font>


🧪 <font color=red> Exemple d'utilisation </font>

API REST pour rédemmarer un job : 


    @RestController
    @RequestMapping("/jobs")
    public class JobManagementController {
    
        @Autowired
        private JobOperator jobOperator;
    
        @PostMapping("/restart/{executionId}")
        public ResponseEntity<String> restartJob(@PathVariable Long executionId) {
            try {
                Long newExecutionId = jobOperator.restart(executionId);
                return ResponseEntity.ok("Job redémarré avec l'ID : " + newExecutionId);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur lors du redémarrage du job : " + e.getMessage());
            }
        }
    }


⚠️ <font color=red> Attention </font>

- Le job doit être conçu pour supporter la reprise (ex: avec un RestartableItemReader).

- Les paramètres initiaux sont réutilisés automatiquement

- Si l'étape échouée n’est pas allowStartIfComplete=true, elle ne sera pas ré-exécutée si elle a réussi