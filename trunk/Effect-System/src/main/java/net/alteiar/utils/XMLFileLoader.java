package net.alteiar.utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLFileLoader {
	
	private Document myXML;
	
	public XMLFileLoader(String path)
	{
		try {
			 
				File fXmlFile = new File(path);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				myXML = dBuilder.parse(fXmlFile);
			 
				//optional, but recommended
				//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
				myXML.getDocumentElement().normalize();
		 
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
	
	public ArrayList<String> getNodeNameInTag(String tag)
	{
		ArrayList<String> result=new ArrayList<String>();
		NodeList list=myXML.getElementsByTagName(tag);
		for(int i=0;i<list.getLength();i++)
		{
			Node temp=list.item(i);
			NodeList children=temp.getChildNodes();
			for(int j=0;j<children.getLength();j++)
			{
				if(!children.item(j).getNodeName().contentEquals("#text"))
				{
					if(children.item(j).getNodeType()==Node.ELEMENT_NODE)
					{
						Element child=(Element) children.item(j);
						if(child.hasAttribute("name"))
						{
							result.add(child.getAttribute("name"));
						}
					}
				}
			}
		}
		return result;
	}

	
	public ArrayList<String> getNodeItemInTag(String tag,String name)
	{
		ArrayList<String> result=new ArrayList<String>();
		NodeList list=myXML.getElementsByTagName(tag);
		for(int i=0;i<list.getLength();i++)
		{
			if(!list.item(i).getNodeName().contentEquals("#text"))
			{
				if(list.item(i).getNodeType()==Node.ELEMENT_NODE)
				{
					Element child=(Element) list.item(i);
					if(child.hasAttribute("name"))
					{
						if(child.getAttribute("name").contentEquals(name))
						{
							result.add(child.getTextContent());
						}
					}
				}
			}
		}
		return result;
	}
	
	public String getNodeItemInTag(String tag,String childTag,String name,String childName)
	{
		String result="";
		NodeList list=myXML.getElementsByTagName(tag);
		for(int i=0;i<list.getLength();i++)
		{
			if(!list.item(i).getNodeName().contentEquals("#text"))
			{
				if(list.item(i).getNodeType()==Node.ELEMENT_NODE)
				{
					Element node=(Element) list.item(i);
					if(node.hasAttribute("name"))
					{
						if(node.getAttribute("name").contentEquals(name))
						{
							NodeList children=node.getChildNodes();
							for(int j=0;j<children.getLength();j++)
							{
								if(!children.item(j).getNodeName().contentEquals("#text"))
								{
									if(children.item(j).getNodeType()==Node.ELEMENT_NODE)
									{
										Element child=(Element) children.item(j);
										if(child.hasAttribute("name"))
										{
											if(child.getNodeName().contentEquals(childTag) && child.getAttribute("name").contentEquals(childName)){
												result=child.getTextContent();
											}
												
										}
									}
							}
						}
					}
				}
			}
		  }
		}
		return result;
	}
	
	public ArrayList<String> getNodesInTagAndName(String tag,String name)
	{
		ArrayList<String> result=new ArrayList<String>();
		NodeList list=myXML.getElementsByTagName(tag);
		for(int i=0;i<list.getLength();i++)
		{
			NodeList children=list.item(i).getChildNodes();
			for(int j=0;j<children.getLength();j++)
			{
				if(!children.item(j).getNodeName().contentEquals("#text"))
				{
					if(children.item(j).getNodeType()==Node.ELEMENT_NODE)
					{
						Element child=(Element) children.item(j);
						if(child.hasAttribute("name"))
						{
							if(child.getAttribute("name").contentEquals(name))
							{
								NodeList elements=child.getChildNodes();
								for(int k=0;k<elements.getLength();k++)
								{
									if(!elements.item(k).getNodeName().contentEquals("#text"))
									{
										if(elements.item(k).getNodeType()==Node.ELEMENT_NODE)
										{
											Element element=(Element) elements.item(k);
											if(element.hasAttribute("name"))
											{
													result.add(element.getAttribute("name"));
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}	

}
