package com.samydevup.myspringbatchproject.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuration du/des jobs ; de ses "steps" (de type Tasklet et/ou Chunked-oriented)
 *
 */

@Configuration
public class SampleJob {

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


    /**
     * Méthode retournant le premier job de notre projet.
     * Nous utilisons le pattern builder pour le créer.
     * Pour ce faire, nous injectons l'interface JobBuilderFactory dans cette classe de configuration.
     *
     * Ici notre Job se nomme "First Job" et lance un step nommé "First Step" instancié par la méthode firstStep
     * @return Job
     */

    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First Job").start(firstStep()).build();
    }

    /**
     * Méthode permettant de créer en seconde étape notre step.
     * Ici notre Step se nomme First Step est du type Tasklet.
     *
     * @return Step
     */
    private Step firstStep() {
        return stepBuilderFactory.get("First Step").tasklet(firstTask()).build();
    }

    /**
     * Méthode permettant de configurer le type de notre step
     * ici le type implémenté est Tasklet.
     *
     * Il ne fait qu'afficher une sortie standard
     * @return Tasklet
     */
    private Tasklet firstTask() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println(" *** this is the first tasklet step *** ");
                return RepeatStatus.FINISHED;
            }
        };
    }

}
