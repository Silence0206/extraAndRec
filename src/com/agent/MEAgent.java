package com.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.io.SAXReader;
import org.dom4j.*;

import sun.rmi.runtime.Log;

import com.bean.Component;
import com.bean.Dependency;

public class MEAgent extends Agent{
	
	public void setup()
	{
		SimpleBehaviour hello_behaviour = new SimpleBehaviour(this)
		{
		boolean finished = false;
		// 覆盖 Behaviour 类的action这一抽象方法
		public Document getDocument() throws DocumentException {
			SAXReader read=new SAXReader();
			return	read.read(this.getClass().getResourceAsStream("ex1.xml"));
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
			List<Dependency> dependencys = new ArrayList<Dependency>();
			List<Element> elementComponentList=new ArrayList<Element>();
			List<Element> elementDependecyList = new ArrayList<Element>();
			try {
				document = this.getDocument();
				Element root = document.getRootElement();  //获取根节点 
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
		            							ACLMessage msg1 = new ACLMessage( ACLMessage.REQUEST );
				            					msg1.addReceiver(new AID("extraAgent", AID.ISLOCALNAME));
				            					msg1.setContent(String.valueOf(e3));
				            					System.out.println("匹配结果: ");
				            					System.out.println("组件编号：101010");
				            					System.out.println("组件名称：AddtoShopcart");
				            					myAgent.send(msg1);
				            					break;
						            		}
		            						else if(e3.getName().equals("Dependency"))
						            		{
				            					elementDependecyList.add(e3);
						            		}
		            					}		
				            		}
		            			}
		            		}
		            	}
		            }
				}
				for(Element item:elementDependecyList)
				{
					Dependency dependency = new Dependency();
					String id = item.attributeValue("xmi.id");
					String isSpecification = item.attributeValue("isSpecification");
					Iterator<Element> iteratorDependency = item.elementIterator();
					String fromId = "";
					String toId = "";
					while(iteratorDependency.hasNext())
					{
						Element e4 = iteratorDependency.next();
						if(e4.getName().equals("Dependency.client"))
	            		{
							Iterator<Element> iteratorDependencyClient = e4.elementIterator();
							while(iteratorDependencyClient.hasNext())
							{
								Element e5 = iteratorDependencyClient.next();
								if(e5.getName().equals("Component"))
			            		{
									fromId = e5.attributeValue("xmi.idref");
			            		}
							}
	            		}
						else if(e4.getName().equals("Dependency.supplier"))
	            		{
							Iterator<Element> iteratorDependencySupplier = e4.elementIterator();
							while(iteratorDependencySupplier.hasNext())
							{
								Element e6 = iteratorDependencySupplier.next();
//								System.out.println("当前节点的名称：" + e6.getName());
								if(e6.getName().equals("Component"))
			            		{
									toId = e6.attributeValue("xmi.idref");
			            		}
							}
	            		}
					}
					dependency.setId(id);
					dependency.setFromId(fromId);
					dependency.setToId(toId);
					dependency.setIsSpecification(isSpecification);
					dependencys.add(dependency);
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
			} 
			try {
				FileOutputStream out;
				out = new FileOutputStream("D:/build.txt");
				PrintStream p=new PrintStream(out);
				for(Dependency dependency:dependencys)
				{
					String fromComponent = "";
					String toComponent = "";
					for(Component componentItem:components)
					{
						
						if(dependency.getFromId().equals(componentItem.getId()))
						{
							fromComponent = componentItem.getName();
						}
						else if(dependency.getToId().equals(componentItem.getId()))
						{
							toComponent = componentItem.getName();
						}
					}
					if(fromComponent!=null&&toComponent!=null)
					{
						p.println(fromComponent+"--->"+toComponent);
					}
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			finished = true;
		}
		// done()在父类中也是一个抽象方法
		public boolean done(){
			return finished;
		}
		};
	addBehaviour(hello_behaviour);
	}	
	//遍历当前节点下的所有节点  
    public void listNodes(Element node){  
        System.out.println("当前节点的名称：" + node.getName());  
        //首先获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();  
        //遍历属性节点  
        for(Attribute attribute : list){  
            System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
        }  
        //如果当前节点内容不为空，则输出  
        if(!(node.getTextTrim().equals(""))){  
             System.out.println( node.getName() + "：" + node.getText());    
        }  
        //同时迭代当前节点下面的所有子节点  
        //使用递归  
        Iterator<Element> iterator = node.elementIterator();  
        while(iterator.hasNext()){  
            Element e = iterator.next();  
            listNodes(e);  
        }  
    }  
}