package tp4;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentVendeur extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agent Vendeur d�marr�: " + getLocalName());

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage requestMsg = new ACLMessage(ACLMessage.REQUEST);
                requestMsg.addReceiver(new jade.core.AID("Commissaire_Priseur", jade.core.AID.ISLOCALNAME));
                requestMsg.setContent("D�marrer l'ench�re pour un produit artisanal.");
                send(requestMsg);
            }
        });
    }
}
