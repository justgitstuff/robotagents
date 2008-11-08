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

         AgentActionSchema as = new AgentActionSchema(TASK);
         add(as, Task.class);
         as.add(TASK_EMPLOYER_ID,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);
         as.add(TASK_PRIORITY,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);

         AgentActionSchema las = new AgentActionSchema(LOCATE_TASK);
         add(las, LocateTask.class);
         las.addSuperSchema(as);
         las.add(LOCATE_TASK_OBJECT_ID,
               (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
               ObjectSchema.MANDATORY);

         AgentActionSchema clas = new AgentActionSchema(CHECK_LOCATION_TASK);
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
         mf.add(MESSAGE_INFO_FACT, (ConceptSchema) getSchema(FACT),
               ObjectSchema.OPTIONAL);
         mf.add(MESSAGE_INFO_LOCATE_TASK,
               (AgentActionSchema) getSchema(LOCATE_TASK),
               ObjectSchema.OPTIONAL);
         mf.add(MESSAGE_INFO_CHECK_LOCATION_TASK,
               (AgentActionSchema) getSchema(CHECK_LOCATION_TASK),
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
