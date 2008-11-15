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
import java.util.Date;

import utils.*;
import ontologies.*;

public class SimpleRobotAgent extends Agent implements RobotsVocabulary
{
   protected int xPos;
   protected int yPos;
   protected int id;
   protected boolean inMove;

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

   protected class EnvRequestBehav extends OneShotBehaviour
   {
      public void action()
      {
         DFAgentDescription dfd = new DFAgentDescription();
         ServiceDescription sd = new ServiceDescription();
         sd.setType("ENVIRONMENT");
         dfd.addServices(sd);

         ArrayList<AID> aids = new ArrayList<AID>();

         try
         {
            DFAgentDescription[] result = DFService.search(myAgent, dfd);
            if (result.length > 0)
               for (int i = 0; i < result.length; i++)
               {
                  MessageInfo m = new MessageInfo(id, 0, xPos, yPos,
                        (float) 1000.0);
                  m.setF(new Fact(id, xPos, yPos, new Date()));
                  /*
                   * MessageInfo m = new MessageInfo(); m.setMainSenderId(id);
                   * m.setMainReceiverId(0); m.setSenderPosX(xPos);
                   * m.setSenderPosY(yPos); m.setSenderRange((float) 1000.0);
                   * Fact tempFact = new Fact(); tempFact.setId(id);
                   * tempFact.setPosX(xPos); tempFact.setPosY(yPos);
                   * tempFact.setTime(new Date()); m.setF(tempFact);
                   */

                  ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                  msg.addReceiver(result[i].getName());
                  msg.setLanguage(codec.getName());
                  msg.setOntology(ontology.getName());
                  msg.setConversationId("forYourEyesOnly");
                  try
                  {
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
         catch (FIPAException e)
         {
            e.printStackTrace();
         }
      }
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
               addBehaviour(new PropBehav(msg));
            }
            else if (msg.getPerformative() == ACLMessage.INFORM)
            {
               try
               {
                  ContentElement content = getContentManager().extractContent(
                        msg);
                  MessageInfo info = (MessageInfo) content;
                  Fact fact = info.getF();

                  facts.add(fact);
                  if (facts.size() > 100) facts.remove(0); 

               }
               catch (Exception ex)
               {
                  ex.printStackTrace();
               }
            }
         }
         else if(!inMove)
         {
            double x = (Math.random()*500);
            double y = (Math.random()*500);
            addBehaviour(new MoveBehav((int)x,(int)y));
         }
         else
         {
            //block();
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

         }
      }
   }

   /*
    * protected class ContractInitBehav extends ContractNetInitiator { }
    * 
    * protected class ContractRespBehav extends ContractNetResponder { }
    */

   protected class MoveBehav extends OneShotBehaviour
   {
      protected int xBeg, xDest, yBeg, yDest;
      protected double x, y, distance;

      public MoveBehav(int xd, int yd)
      {
         xBeg = xPos;
         yBeg = yPos;
         xDest = xd;
         yDest = yd;
         distance = Math.sqrt((xBeg - xDest) * (xBeg - xDest) + (yBeg - yDest)
               * (yBeg - yDest)) / 10;
         inMove = true;
      }
      
      public void action()
      {
         System.out.println("Started moving to " + xDest + " " + yDest);
         x = xBeg;
         y = yBeg;
         TickerBehaviour loop = new TickerBehaviour(myAgent, 100)
         {
           public void onTick()
           {
              if(Math.abs(xPos - xDest) < 10 && Math.abs(yPos - yDest) < 10)
              {
                 System.out.println("Ended moving on " + xPos + " " + yPos);
                 inMove = false;
                 stop();
              }
              else
              {
                 x += (xDest - xBeg)/distance;
                 y += (yDest - yBeg)/distance;
                 xPos = (int)x;
                 yPos = (int)y;
                 addBehaviour(new EnvRequestBehav());
              }
           }
           
         };
         addBehaviour(loop);
      }

   }

   public void setup()
   {
      Object[] args = getArguments();
      if (args != null && args.length > 0)
      {
         id = Integer.parseInt((String) args[0]);
         xPos = Integer.parseInt((String) args[1]);
         yPos = Integer.parseInt((String) args[2]);
      }

      tasks = new ArrayList<Task>();
      facts = new ArrayList<Fact>();
      conversations = new ArrayList<String>();
      inMove = false;
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

      addBehaviour(new EnvRequestBehav());
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
