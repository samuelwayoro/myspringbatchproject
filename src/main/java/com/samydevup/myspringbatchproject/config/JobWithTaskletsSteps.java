package com.samydevup.myspringbatchproject.config;

import com.samydevup.myspringbatchproject.listner.FirstJobListner;
import com.samydevup.myspringbatchproject.listner.FirstStepListner;
import com.samydevup.myspringbatchproject.service.SecondTask;
import com.samydevup.myspringbatchproject.service.ThirdTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe d'exemple de configuration du job (FirstJob) et des Steps de type TaskletStep
 */

@Configuration
public class JobWithTaskletsSteps {

    private static Logger logger = LoggerFactory.getLogger(JobWithTaskletsSteps.class);

    /**
     * Objet à intégrer en premier lieu.
     * Il permet de créer des Job de manière simple, propre et intégrée à Spring
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * Objet à injecter en deuxième lieu : permettant de créer les steps de notre Job
     * ici pour le premier step implémenté (firstStep) est du type TaskLet d'ou la création
     * plus en bas d'un objet de type Tasklet.
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    /***
     * INJECTION DES DEUX TASKLET
     * IMPLEMENTEES DEPUIS LE PACKAGE SERVICE
     */
    @Autowired
    private SecondTask secondTasklet;

    @Autowired
    private ThirdTask thirdTasklet;

    /**
     * Injection des Listner de mon Job :
     * - FirstJobListener : Listener pour le Job
     * - FirstStepListner : Listener pour le Step
     */

    @Autowired
    private FirstJobListner firstJobListner;

    @Autowired
    private FirstStepListner firstStepListner;


    /**
     * Méthode retournant le premier job de notre projet.
     * Nous utilisons le pattern builder pour le créer.
     * Pour ce faire, nous injectons l'interface JobBuilderFactory dans cette classe de configuration.
     * <p>
     * Ici notre Job se nomme "First Job" et lance un step nommé "First Step" instancié par la méthode firstStep
     *
     * @return Job
     */

    @Bean
    public Job firstJob() {
        logger.info("✨✨✨ démarrage de firstJob  de JobWithTaskletsSteps  ");
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer()) //rajoute le paramètre run.id qui s'auto-incrémente dans la bd
                .start(firstStep())
                .next(secondStep())
                .next(thirdStep())
                .listener(firstJobListner)
                .build();
    }


    /**
     * Méthode permettant de créer en seconde étape notre step.
     * Ici notre Step se nomme First Step est du type Tasklet.
     *
     * @return Step
     */

    private Step firstStep() {
        logger.info("👉 firstStep de JobWithTaskletsSteps en cours ... ");
        return stepBuilderFactory
                .get("First Step")
                .tasklet(firstTask())
                .listener(firstStepListner)//câblage du listner (firstStepListner)
                .build();
    }

    /**
     * Méthode permettant de configurer le type de notre step
     * ici le type implémenté est Tasklet.
     * <p>
     * Il ne fait qu'afficher une sortie standard
     *
     * @return Tasklet
     */
    private Tasklet firstTask() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                logger.info("🔜 firstTask de jobWithTaskletsSteps en cours ...  ");
                logger.info("contenu de son context {} ", chunkContext.getStepContext().getStepExecutionContext());
                return RepeatStatus.FINISHED;
            }
        };
    }


    /***
     * Création d'un second step lui aussi de type Tasklet
     * implémenté ci-dessous.
     *
     * NB : Ce step utilise des données rajoutées dans le context à partir du listner du job (FirsJobListner)...
     *      Ils sont implémentés dans le code du task de ce Step (secondTask).
     */

    private Step secondStep() {
        logger.info("👉 secondStep de JobWithTaskletsSteps en cours ... ");
        return stepBuilderFactory
                .get("Second step")
                .tasklet(secondTasklet)
                .build();
    }

    /**
     * private Tasklet secondTask() {
     * return new Tasklet() {
     *
     * @Override public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
     * System.out.println("--- THIS IS THE SECOND TASKLET STEP ---");
     * return RepeatStatus.FINISHED;
     * }
     * };
     * }
     */

    private Step thirdStep() {
        logger.info("👉 thirdStep de JobWithTaskletsSteps en cours ... ");
        return stepBuilderFactory
                .get("third step")
                .tasklet(thirdTasklet)
                .build();
    }

    /*
    private Tasklet thridTask() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("*-*- THIS IS THE THIRD TASKLET STEP *-*-");
                return RepeatStatus.FINISHED;
            }
        };
    }
    */

}
