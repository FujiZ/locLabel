package locLabel;

import org.jsoup.nodes.Element;

public class LocNode {
	/*
	public static final String CountryRegion="CountryRegion";
	public static final String State="State";
	public static final String City="City";
	public static final String Region="Region";
	*/
	
	public final String tag;
	public final String name;
	public final String code;
	
	private LocNode(String tag,String name,String code){
		this.tag=tag;
		this.name=name;
		this.code=code;
	}
	
	public static LocNode parseNode(Element locElement){
		String tagString=locElement.tagName();
		if(tagString.equalsIgnoreCase("Location"))
			return null;
		String nameString=locElement.attr("Name");
		String codeString=locElement.attr("Code");
		return new LocNode(tagString, nameString, codeString);
	}
}
