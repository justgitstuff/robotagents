package environment;

import java.util.ArrayList;
import java.util.Date;

import utils.*;
import ontologies.*;
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.Codec.*;
import jade.content.lang.sl.*;
import jade.content.onto.*;
import jade.content.onto.basic.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import jade.lang.acl.ACLMessage;

public class EnvironmentAgent extends Agent implements RobotsVocabulary
{
   protected ArrayList<EnvObject> envState;
   protected Codec codec = new SLCodec();
   protected Ontology ontology = RobotsOntology.getInstance();

   protected class SendBehav extends OneShotBehaviour
   {
      public void action()
      {
         MessageInfo m = new MessageInfo(0, 1, 0, 0, (float) 10.0);
         m.setF(new Fact(1, 2, 3, new Date()));
         ACLMessage msg = new ACLMessage(ACLMessage.PROPAGATE);
         msg.addReceiver(new AID("A1", AID.ISLOCALNAME));
         msg.setLanguage(codec.getName());
         msg.setOntology(ontology.getName());
         msg.setConversationId("test");
         try
         {
            //getContentManager().fillContent(msg,
                  //new Action(new AID("A1", AID.ISLOCALNAME), m));
            getContentManager().fillContent(msg, m);
            send(msg);
         }
         catch (CodecException ce)
         {
            ce.printStackTrace();
         }
         catch (OntologyException oe)
         {
            oe.printStackTrace();
         }

      }
   }

   protected class EnvUpdateBehav extends CyclicBehaviour
   {
      public void action()
      {
         ACLMessage msg = receive();

         if (msg != null)
         {
            if (msg.getContent() != null)
            {
               if (msg.getPerformative() == ACLMessage.INFORM)
               {

                  String content = msg.getContent();
               }

            }
         }

      }
   }

   public void setup()
   {
      getContentManager().registerLanguage(codec);
      getContentManager().registerOntology(ontology);

      ServiceDescription sd = new ServiceDescription();
      sd.setType("ENVIRONMENT");
      sd.setName(getLocalName());

      DFAgentDescription dfd = new DFAgentDescription();
      dfd.setName(getAID());
      dfd.addServices(sd);

      try
      {
         DFService.register(this, dfd);
      }
      catch (FIPAException fe)
      {
         fe.printStackTrace();
      }

      addBehaviour(new SendBehav());
   }

   protected void takeDown()
   {
      try
      {
         DFService.deregister(this);
      }
      catch (Exception e)
      {
      }
   }

}
