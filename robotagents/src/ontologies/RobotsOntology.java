package ontologies;

import jade.content.onto.*;
import jade.content.schema.*;
import utils.*;

public class RobotsOntology extends Ontology implements RobotsVocabulary
{
   public static final String ONTOLOGY_NAME = "Robots-Ontology";
   private static Ontology instance = new RobotsOntology();

   public static Ontology getInstance()
   {
      return instance;
   }

   private RobotsOntology()
   {
      super(ONTOLOGY_NAME, BasicOntology.getInstance());

      try
      {
         ConceptSchema cs = new ConceptSchema(ENV_OBJECT);
         add(cs, EnvObject.class);
         cs.add(ENV_OBJECT_ID,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         cs.add(ENV_OBJECT_POS_X,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         cs.add(ENV_OBJECT_POS_Y,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);

         ConceptSchema fact = new ConceptSchema(FACT);
         add(fact, Fact.class);
         fact.addSuperSchema(cs);
         fact.add(FACT_TIME, (PrimitiveSchema) getSchema(BasicOntology.DATE),
               ObjectSchema.MANDATORY);

         ConceptSchema as = new ConceptSchema(TASK);
         add(as, Task.class);
         as.add(TASK_EMPLOYER_ID,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         as.add(TASK_PRIORITY,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);

         ConceptSchema las = new ConceptSchema(LOCATE_TASK);
         add(las, LocateTask.class);
         las.addSuperSchema(as);
         las.add(LOCATE_TASK_OBJECT_ID,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);

         ConceptSchema clas = new ConceptSchema(CHECK_LOCATION_TASK);
         add(clas, CheckLocationTask.class);
         clas.addSuperSchema(as);
         clas.add(CHECK_LOCATION_TASK_POS_X,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         clas.add(CHECK_LOCATION_TASK_POS_Y,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);

         PredicateSchema mf = new PredicateSchema(MESSAGE_INFO);
         add(mf, MessageInfo.class);
         mf.add(MESSAGE_INFO_MAIN_SENDER_ID,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         mf.add(MESSAGE_INFO_MAIN_RECEIVER_ID,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         mf.add(MESSAGE_INFO_SENDER_POS_X,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         mf.add(MESSAGE_INFO_SENDER_POS_Y,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         mf.add(MESSAGE_INFO_SENDER_RANGE,
               (PrimitiveSchema) getSchema(BasicOntology.FLOAT),
               ObjectSchema.MANDATORY);
         mf.add(MESSAGE_INFO_DEADLINE,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.OPTIONAL);
         mf.add(MESSAGE_INFO_TASKCOMPLETION,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.OPTIONAL);
         mf.add(MESSAGE_INFO_ACOMPLISHMENT,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.OPTIONAL);
         mf.add(MESSAGE_INFO_FACT, (ConceptSchema) getSchema(FACT),
               ObjectSchema.OPTIONAL);
         mf.add(MESSAGE_INFO_LOCATE_TASK,
               (ConceptSchema) getSchema(LOCATE_TASK),
               ObjectSchema.OPTIONAL);
         mf.add(MESSAGE_INFO_CHECK_LOCATION_TASK,
               (ConceptSchema) getSchema(CHECK_LOCATION_TASK),
               ObjectSchema.OPTIONAL);
      }
      catch (OntologyException oe)
      {
         oe.printStackTrace();
      }
      catch (Exception fe)
      {
         fe.printStackTrace();
      }
   }

}
