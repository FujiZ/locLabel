package locLabel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLBulider {
	private File outputFile;
	private Document document=new Document();
	private Element root=new Element("Location");
	
	public XMLBulider(File outputFile){
		this.outputFile=outputFile;
		document.setRootElement(root);
	}
	
	public void addPath(List<LocNode> nodeList){
		Iterator<LocNode> nodeIterator=nodeList.iterator();
		Element curElement=root;
		while(nodeIterator.hasNext()){
			LocNode curNode=nodeIterator.next();
			Element temElement;
			if((temElement=containsChild(curElement,curNode))==null){
				temElement=new Element(curNode.tag);
				temElement.setAttribute("Name", curNode.name);
				temElement.setAttribute("Code",curNode.code);
				curElement.addContent(temElement);
			}
			curElement=temElement;
		}
	}
	
	private Element containsChild(Element element,LocNode node){
		List<Element> children=element.getChildren();
		Iterator<Element> eleIterator=children.iterator();
		while(eleIterator.hasNext()){
			Element curChild=eleIterator.next();
			if(curChild.getAttribute("Name").getValue().equals(node.name))
				return curChild;
		}
		return null;
	}
	
	public void build() throws IOException{
		Format format = Format.getCompactFormat(); 
		format.setEncoding("UTF-8"); 
		format.setIndent("    "); 
		XMLOutputter outputter = new XMLOutputter(format);
		outputter.output(document, new FileOutputStream(outputFile)); 
	}
}
