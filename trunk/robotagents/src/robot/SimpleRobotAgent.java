package robot;

import jade.core.*;
import jade.core.behaviours.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.*;
import jade.content.onto.*;
import jade.content.onto.basic.*;
import jade.lang.acl.*;

import java.util.ArrayList;
import utils.*;
import ontologies.*;

public class SimpleRobotAgent extends Agent implements RobotsVocabulary
{
   protected int xPos;
   protected int yPos;
   protected int id = 0;

   protected ArrayList<Task> tasks;
   protected ArrayList<Fact> facts;
   protected ArrayList<String> conversations;

   protected Codec codec = new SLCodec();
   protected Ontology ontology = RobotsOntology.getInstance();
   protected MessageTemplate mt = MessageTemplate.and(MessageTemplate
         .MatchLanguage(codec.getName()), MessageTemplate
         .MatchOntology(ontology.getName()));

   public int getId()
   {
      return this.id;
   }

   public boolean propagated(String convID)
   {
      return this.conversations.contains(convID);
   }

   public void addConvId(String convID)
   {
      if (!propagated(convID))
         this.conversations.add(convID);
   }

   public void list()
   {
      for (int i = 0; i < this.conversations.size(); i++)
         System.out.println(" - " + i + " " + this.conversations.get(i));
   }

   protected class SimpleBehav extends CyclicBehaviour
   {
      public void action()
      {
         ACLMessage msg = receive(mt);
         if (msg != null)
         {
            if (msg.getPerformative() == ACLMessage.PROPAGATE)
            {
               // System.out
               // .println("__________________________________________");
               addBehaviour(new PropBehav(msg));
            }
         }
         else
         {
            block();
         }
      }
   }

   protected class PropBehav extends OneShotBehaviour
   {
      ACLMessage message;

      public PropBehav(ACLMessage msg)
      {
         message = new ACLMessage(ACLMessage.PROPAGATE);
         message.setConversationId(msg.getConversationId());
         message.setLanguage(codec.getName());
         message.setOntology(ontology.getName());
         try
         {
            ContentElement ce = getContentManager().extractContent(msg);
            getContentManager().fillContent(message, ce);
         }
         catch (CodecException ce)
         {
            ce.printStackTrace();
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

      public void action()
      {
         // System.out.println("***" +
         // propagated(message.getConversationId()));
         // list();
         if (!propagated(message.getConversationId()))
         {
            // System.out.println("costam dalej robie");
            DFAgentDescription dfd = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("ROBOT");
            dfd.addServices(sd);

            ArrayList<AID> aids = new ArrayList<AID>();

            try
            {
               DFAgentDescription[] result = DFService.search(myAgent, dfd);
               // System.out.println("### " + result.length + " ###");
               if (result.length > 0)
                  for (int i = 0; i < result.length; i++)
                  {
                     // System.out.println("myAgent.getAID: " +
                     // myAgent.getAID()
                     // +
                     // "result[i]: " + result[i].getName() );
                     if (!myAgent.getAID().equals(result[i].getName()))
                     {
                        aids.add(result[i].getName());
                        // System.out.println("DORZUCAM!!!");
                     }
                  }

            }
            catch (FIPAException e)
            {
               e.printStackTrace();
            }
            catch (Exception fe)
            {
               fe.printStackTrace();
            }

            for (int i = 0; i < aids.size(); i++)
            {
               message.addReceiver(aids.get(i));
               System.out.println(aids.get(i));
            }

            addConvId(message.getConversationId());

            send(message);

         } // else
         // System.out
         // .println("nie wiadomo dlaczego, wyglada na to ze juz to
         // przekazywalem");
      }
   }

   /*
    * protected class ContractInitBehav extends ContractNetInitiator { }
    *
    * protected class ContractRespBehav extends ContractNetResponder { }
    */

   protected void move(int x, int y)
   {

   }

   public void setup()
   {
      Object[] args = getArguments();
      if (args != null && args.length > 0)
      {
         id = Integer.parseInt((String) args[0]);
      }

      tasks = new ArrayList<Task>();
      facts = new ArrayList<Fact>();
      conversations = new ArrayList<String>();

      getContentManager().registerLanguage(codec);
      getContentManager().registerOntology(ontology);

      ServiceDescription sd = new ServiceDescription();
      sd.setType("ROBOT");
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
      catch (Exception fe)
      {
         fe.printStackTrace();
      }

      addBehaviour(new SimpleBehav());
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
