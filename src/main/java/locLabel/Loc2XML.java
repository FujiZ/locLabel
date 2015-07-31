package locLabel;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class Loc2XML {
	public static Document locData;
	public static List<String> locStrList;
	private File outputFile;
	
	static{
		try {
			locData=Jsoup.parse(new FileInputStream(LocLabel.locFile), "UTF-8", "", Parser.xmlParser());
			locStrList=new LinkedList<String>();
			Elements locElements=locData.select("[Name]");
			Iterator<Element> eleIterator=locElements.iterator();
			while(eleIterator.hasNext()){
				Element curElement=eleIterator.next();
				locStrList.add(curElement.attr("Name"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Loc2XML(String name){
		outputFile=new File(name+".xml");
	}
	
	public Loc2XML(){
		outputFile=new File("temp.xml");
	}
	
	public void buildXMLDoc(List<String> locList) throws IOException{
		if(!outputFile.createNewFile()){
			outputFile.delete();
			outputFile.createNewFile();
		}
		XMLBulider xmlBulider=new XMLBulider(outputFile);
		Iterator<String> locIterator=locList.iterator();
		while(locIterator.hasNext()){
			String locStr=locIterator.next();
			List<LocNode> nodeList=getNodeList(locStr);
			xmlBulider.addPath(nodeList);
		}
		xmlBulider.build();
	}
	
	public Map<BiLocNode,BiLocNode> buildBiNodeMap(List<String> locList){
		Map<BiLocNode, BiLocNode> nodeMap=new HashMap<BiLocNode, BiLocNode>();
		Iterator<String> locIterator=locList.iterator();
		while(locIterator.hasNext()){
			String locStr=locIterator.next();
			List<LocNode> nodeList=getNodeList(locStr);
			LinkedList<BiLocNode> biNodeList=getBiNodeList(nodeList);
			nodeMap.put(biNodeList.getLast(), biNodeList.getFirst());
		}
		return nodeMap;
	}
	
	
	private List<LocNode> getNodeList(String locStr){
		LinkedList<LocNode> nodeList=new LinkedList<LocNode>();
		//查找符合locStr的节点
		Element locElement=locData.select("[Name="+locStr+"]").first();
		if(locElement!=null){
			nodeList.addFirst(LocNode.parseNode(locElement));
			while((locElement=locElement.parent())!=null){
				LocNode curNode=LocNode.parseNode(locElement);
				if(curNode!=null)
					nodeList.addFirst(curNode);
				else
					break;
			}
		}
		return nodeList;
	}
	
	private LinkedList<BiLocNode> getBiNodeList(List<LocNode> nodeList){
		LinkedList<BiLocNode> biNodeList=new LinkedList<BiLocNode>();
		if(nodeList==null||nodeList.isEmpty())
			return biNodeList;
		Iterator<LocNode> nodeIterator=nodeList.iterator();
		LocNode curNode;
		Element locElement = null;
		if(nodeIterator.hasNext()){
			curNode=nodeIterator.next();
			locElement=locData.select(curNode.tag+"[Code="+curNode.code+"]:not([Name="+curNode.name+"])").first();
			biNodeList.add(BiLocNode.parseNode(locElement, curNode));
		}
		
		while(nodeIterator.hasNext()){
			curNode=nodeIterator.next();
			if(curNode.code.isEmpty())
				continue;
			locElement=locElement.select(curNode.tag+"[Code="+curNode.code+"]:not([Name="+curNode.name+"])").first();
			biNodeList.add(BiLocNode.parseNode(locElement, curNode));
		}
		
		return biNodeList;
	}
	
	
	
	
}
