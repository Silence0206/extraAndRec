package com.agent;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import jade.core.*;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
public class transferAgent extends Agent{
	public void setup()
	{
	SimpleBehaviour hello_behaviour = new SimpleBehaviour(this)
	{
		boolean finished = false;
		public Document getDocument() throws DocumentException {
			SAXReader read=new SAXReader();
			return	read.read(this.getClass().getResourceAsStream("ex2.xml"));
		}
		public void action(){
			Document document;
			System.out.println("My local name is:"+getLocalName());
			System.out.println("My globally unique name is:"+getName() );
			Location l = here();
			System.out.println("I am running in a location called:"+l.getName());
			System.out.println("Which is identified uniquely as:"+l.getID());
			System.out.println("And is contactable at:"+l.getAddress());
			System.out.println("Using the protocol:"+l.getProtocol());
			try {
				document = this.getDocument();
				ACLMessage msg = new ACLMessage( ACLMessage.REQUEST );
				msg.addReceiver(new AID("extraAgent", AID.ISLOCALNAME));
				msg.setContent(String.valueOf(document));
				System.out.println("tranferAgent:send: "+String.valueOf(document));
				myAgent.send(msg);
			}catch (DocumentException e1) {
				e1.printStackTrace();
			} //获取文件
			finished = true;
		}
		// done()在父类中也是一个抽象方法
	public boolean done(){
		return finished;
		}
	};
	addBehaviour(hello_behaviour);
	}
}
