# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 21 : Configurer un flateFileItemReader</font>

<font color=red> 📘 Qu'est-ce que FlatFileItemReader ? </font>

FlatFileItemReader<T> est un composant de Spring Batch qui permet de lire des fichiers plats ligne par ligne comme :

* des fichiers CSV
* des fichiers texte délimités (, ; \t)
* ou fixes (positions fixes)

👉 Il lit chaque ligne et la transforme en objet Java de type T.


<font color=red> ✅ À quoi ça sert ? </font>

C’est l’un des ItemReader les plus courants pour lire des fichiers de données dans un job batch.

Exemples :

* lecture d'un fichier CSV d'utilisateurs
* lecture d'un journal d'activités 
* import de données métiers 

<font color=red> 🔧 Etapes de configuration (lecture d’un fichier CSV) </font>

1. Créer un dossier interne au projet, contenant le fichier à lire et l'y ajouter (bien formaté). ex : le fichier students.csv dans le dossier inputFiles
2. Créer une classe métier (bean) représentant une ligne du fichier à lire dans un package nommé model (ex : notre classe StudentCsv dont les champs équivalent à une ligne dans le fichier plat student.csv)
3. Création d'une méthode reader, pour la lecture du fichier. Cette configuration en fonction de la version de java utilisé doit contenir les éléments suivant : 

| Option                | Rôle                                          |
|-----------------------|-----------------------------------------------|
| `resource(...)`       | Chemin du fichier à lire                      |
| `delimited()`         | Spécifie que les champs sont séparés (CSV)    |
| `names(...)`          | Colonnes du fichier → noms des attributs Java |
| `targetType(...)`     | Classe de mappage                             |
| `linesToSkip(...)`    | Ignorer les lignes d’en-tête                  |
| `fieldSetMapper(...)` | Mapper personnalisé (optionnel)               |


<i> Exemple </i> 

* Exemple 1 : notre méthode flateFileItemReader dans la classe SampleJob


* Exemple 2 : configuration d'une méthode clientReader 


    @Bean
    @StepScope
    public FlatFileItemReader<Client> clientReader(
            @Value("#{jobParameters['filename']}") String filename) {
        return new FlatFileItemReaderBuilder<Client>()
                .name("clientReader")
                .resource(new FileSystemResource(filename)) // ou ClassPathResource
                .delimited()
                .names("id", "nom", "email") // doit correspondre aux noms des attributs
                .targetType(Client.class)
                .linesToSkip(1) // ignore l’en-tête
                .build();
    }


- L'exemple ci-dessus utilise la version de code la plus récente. Et elle utilise : 

  - <font color=yellow> @Value("#{jobParameters['filename']}") TypeDeRessource nomRessource</font> : pour paramétrer la valeur du fichier à lire. 
      De plus "#{jobParamters[filename]}" signifie que l'application se lance à partir d'une variable d'environnement entrée dans le run de l'ide 
  - <font color=yellow> @Bean </font>: pour ajouter ce Reader dans le context spring afin de l'utiliser à une étape préciser
  - <font color=yellow> @StepScope </font>: pour mentionner que ce bean sera lancé pendant un Step (Chunck Step) du job précisément lors de la lecture des items source.  
  - <font color=yellow>.delimiter() : sert a configurer le délimiteur utilisé dans le fichier csv source</font>
    
