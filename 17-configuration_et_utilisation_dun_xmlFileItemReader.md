# <font color=green> myspringbatchproject 🎯 </font>

<b>

### 📚 <font color=green> étape 23 : Configurer un xmlItemReader</font>

<font color=red> 📘 Qu'est-ce que xmlItemReader ? </font> 

Pour lire un fichier XML dans Spring Batch, tu utilises un composant appelé : 

- StaxEventItemReader< T > : STAX = Streaming Api for XML

Ce composant lit des éléments XML un par un, et les désérialise en objets Java grâce à JAXB (ou un autre convertisseur comme XStream).


<font color=red> 📘 Les étapes à suivre pour utilier un xmlItemReader </font> 

✅ 1 - créer le fichier source xml bien formée  (exemple le fichier inputFiles/students.xml)

✅ 2 - Créer une classe modèle, avec annotation JaxB (exemple : StudentsXml)

✅ 3 - importer les dépendances maven : <i> org.glassfish.jaxb </i>  et <i> spring-oxm </i> pour l'utilisation des annotations jaxb dans la classe modèle et les mapping (marshalling / unMarshalling)

✅ 4 - coder le reader (exemple : la méthode staxEventItemReader dans la classe SampleJob)

    @Bean
    @StepScope
    public StaxEventItemReader<StudentXml> staxEventItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {

        StaxEventItemReader<StudentXml> staxEventItemReader = new StaxEventItemReader<>();

        staxEventItemReader.setResource(fileSystemResource);
        staxEventItemReader.setFragmentRootElementName("student");

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(StudentXml.class);//classe à utiliser pour l'opération de mappage

        staxEventItemReader.setUnmarshaller(marshaller);
        return staxEventItemReader;
    }

🧠 Explication des lignes clés : 

* StaxEventItemReader<StudentXml> : lit le XML de façon streamée (léger en mémoire).
* .setFragmentRootElementName("student") : indique que chaque <student>...</student> est un élément à transformer en objet.
* Jaxb2Marshaller : convertit l'élément XML en objet Java via JAXB.


✅ 5 - utiliser le reader dans le Step de notre job (exemple : voir firstChunkStep dans la classe SampleJob)




⚠️ <font color=red> Attention : </font>

- Le XML doit avoir une structure régulière, chaque fragment <personne> devant pouvoir être converti.

- N’oublie pas les annotations JAXB dans la classe cible (@XmlRootElement, @XmlElement).

- JAXB nécessite des getters et setters publics.
- Ne pas oublier de bien utiliser le fichier source xml en tant que jobParameter dans l'IDE.