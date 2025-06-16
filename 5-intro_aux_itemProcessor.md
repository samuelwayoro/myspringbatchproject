# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 12 : Intro au itemProcessor dans Spring batch </font>

Un ItemProcessor est un composant optionnel dans Spring Batch qui permet de transformer, filtrer ou valider un item entre la lecture (ItemReader) et l’écriture (ItemWriter).



✅ <font color=red>  À quoi ça sert ? </font>

* Modifier un objet (ex. : mettre un nom en majuscules)
* Filtrer un item (en retournant null)
* Appliquer des règles métiers ou de la validation


  <font color=red> NB : </font> Toute itemProcessor implémente l'interface ItemProcessor 

✅ <font color=red> Comment créer/ajouter des itemReader dans un projet spring-batch </font>

1. créer un package pour ses itemProcessor (ici processor)
2. y ajouter la/les classe(s) implémentant l'interface ItemProcessor, sans oublier l'annotation @Component
3. Redéfinir la méthode, en fonction process() du type de reader (voir les différents types plus bas...)

🧱 <font color=red>Interface</font>

        public interface ItemProcessor<I, O> {
            O process(I item) throws Exception;
        }

* I = type de l'objet lu 
* O = type de l'objet écrit 
* Si process() retourne null, l'item est ignoré (non écrit)


🧪 <font color=red> Exemple 1 : mettre les noms en majuscules </font>

        @Component
        public class UpperCaseProcessor implements ItemProcessor<String, String> {
            @Override
            public String process(String item) {
                return item.toUpperCase();
            }
        }

Si on lit "alice", on écrira "ALICE".

🧪 <font color=red> Exemple 2 : filtrer les montants < 100 </font>


        @Component
        public class MontantProcessor implements ItemProcessor<Integer, Integer> {
            @Override 
            public Sting process(Integer integer) {
                return (montant > 100) ? montant : null;
            }
        }

Montants inférieurs à 100 seront ignorés.

🧪 <font color=red> Exemple 3 : transformer une entité Client en un DTO ClientDto </font>

        public class Client {
            private String nom;
            private String email;
            //...
        }
        
        public class ClientDto {
            private String nomComplet;
            //...
        }

        @Component
        public class ClientDtoItemProcessor implements ItemProcessor <Client,ClientDto> {
            
            @Override
            public ClientDto process (Client client) {
                ClientDto clientDto = new ClientDto();
                clientDto.setNomComplet(client.getNom().toUpperCase());
                return clientDto ;
            }
        }


🧠 <font color=red> Résumé</font>

| Élément         | Rôle                                    |
| --------------- | --------------------------------------- |
| `ItemReader`    | Lit un item depuis la source            |
| `ItemProcessor` | (Optionnel) transforme ou filtre l’item |
| `ItemWriter`    | Écrit l’item transformé                 |


