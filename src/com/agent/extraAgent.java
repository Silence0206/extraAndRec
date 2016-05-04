package com.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;

import jade.lang.acl.ACLMessage;
import jade.core.behaviours.SimpleBehaviour;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.io.SAXReader;
import org.dom4j.*;

import sun.rmi.runtime.Log;

import com.bean.Component;

public class extraAgent extends Agent{
	
	public void setup()
	{
		SimpleBehaviour hello_behaviour = new SimpleBehaviour(this)
		{
		boolean finished = false;
		// ���� Behaviour ���action��һ���󷽷�
		public Document getDocument() throws DocumentException {
			SAXReader read=new SAXReader();
			return	read.read(this.getClass().getResourceAsStream("ex2.xml"));
		}
		public void action(){
			System.out.println("My local name is:"+getLocalName());
			System.out.println("My globally unique name is:"+getName() );
			Location l = here();
			System.out.println("I am running in a location called:"+l.getName());
			System.out.println("Which is identified uniquely as:"+l.getID());
			System.out.println("And is contactable at:"+l.getAddress());
			System.out.println("Using the protocol:"+l.getProtocol());
			Document document;
			List<Component> components = new ArrayList<Component>();
			List<Element> elementComponentList=new ArrayList<Element>();
			List<Element> elementDependecyList = new ArrayList<Element>();
			try {
				document = this.getDocument();
				Element root = document.getRootElement();  //��ȡ���ڵ� 
				Iterator<Element> iteratorRoot = root.elementIterator();
				while(iteratorRoot.hasNext())
				{  
		            Element e = iteratorRoot.next();
		            if(e.getName().equals("XMI.content"))
		            {
		            	Iterator<Element> iteratorXMIContent = e.elementIterator();
		            	while(iteratorXMIContent.hasNext())
		            	{
		            		
		            		Element e1 = iteratorXMIContent.next();
		            		if(e1.getName().equals("Model"))
		            		{	
		            			Iterator<Element> iteratorUMLModel = e1.elementIterator();
		            			while(iteratorUMLModel.hasNext())
		            			{
		            				Element e2 = iteratorUMLModel.next();
		            				if(e2.getName().equals("Namespace.ownedElement"))
				            		{
		            					Iterator<Element> iteratorUMLNamespace = e2.elementIterator();
		            					
		            					while(iteratorUMLNamespace.hasNext())
		            					{
		            						Element e3 = iteratorUMLNamespace.next();
				            				if(e3.getName().equals("Component"))
						            		{
				            					elementComponentList.add(e3);
						            		}
		            					}		
				            		}
		            			}
		            		}
		            	}
		            }
				}
		            
				for(Element item:elementComponentList)
				{
					Component component = new Component();
					String name = item.attributeValue("name");
					String xmlId = item.attributeValue("xmi.id");
					String isSpecification = item.attributeValue("isSpecification");
					String isRoot = item.attributeValue("isRoot");
					String isLeaf = item.attributeValue("isLeaf");
					String isAbstract = item.attributeValue("isAbstract");
					component.setName(name);
					component.setId(xmlId);
					component.setIsAbstract(isAbstract);
					component.setIsLeaf(isLeaf);
					component.setIsRoot(isRoot);
					component.setIsSpecification(isSpecification);
					components.add(component);
				}
			}catch (DocumentException e1) {
				e1.printStackTrace();
			} //��ȡ�ļ�
			try {
				FileOutputStream out;
				out = new FileOutputStream("D:/component.txt");
				PrintStream p=new PrintStream(out);
				for(Component componentItem:components)
				{
				p.println(componentItem.getName()+":{");
				p.println("name:"+componentItem.getName()+",");
				p.println("xmi.id:"+componentItem.getId()+",");
				p.println("isSpecification:"+componentItem.getIsSpecification()+",");
				p.println("isAbstract:"+componentItem.getIsAbstract()+",");
				p.println("isRoot:"+componentItem.getIsRoot()+",");
				p.println("isLeaf:"+componentItem.getIsLeaf()+",");
				p.println("};");
				}
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			for(Component componentItem:components){
				ACLMessage msg = new ACLMessage( ACLMessage.REQUEST );
				msg.addReceiver(new AID("buildAgent", AID.ISLOCALNAME));
				msg.setContent(String.valueOf(componentItem));
				System.out.println("extraAgent:send: "+String.valueOf(componentItem));
				myAgent.send(msg);
			}
			finished = true;
		}
		// done()�ڸ�����Ҳ��һ�����󷽷�
		public boolean done(){
			return finished;
		}
		};
	addBehaviour(hello_behaviour);
	}	
	//������ǰ�ڵ��µ����нڵ�  
    public void listNodes(Element node){  
        System.out.println("��ǰ�ڵ�����ƣ�" + node.getName());  
        //���Ȼ�ȡ��ǰ�ڵ���������Խڵ�  
        List<Attribute> list = node.attributes();  
        //�������Խڵ�  
        for(Attribute attribute : list){  
            System.out.println("����"+attribute.getName() +":" + attribute.getValue());  
        }  
        //�����ǰ�ڵ����ݲ�Ϊ�գ������  
        if(!(node.getTextTrim().equals(""))){  
             System.out.println( node.getName() + "��" + node.getText());    
        }  
        //ͬʱ������ǰ�ڵ�����������ӽڵ�  
        //ʹ�õݹ�  
        Iterator<Element> iterator = node.elementIterator();  
        while(iterator.hasNext()){  
            Element e = iterator.next();  
            listNodes(e);  
        }  
    }  
}