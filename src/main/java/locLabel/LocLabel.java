package locLabel;

import java.io.File;
import java.util.List;
import java.util.Map;


public class LocLabel {
	public static final String dirPath="/";//dir to be scanned
	public static final File dir=new File(dirPath);
	public static final File locFile=new File(LocLabel.class.getResource("/LocList.xml").getPath());
	
	public static void main(String[] args) {
		//resolveFile();
		Map<BiLocNode, BiLocNode> nodeMap=resolveString("【环球军事报道】据加拿大《汉和防务评论》8月刊报道，中国正在以空前的速度建造7000吨以上的大型水面舰艇。2015年在大连、上海同时开工建造的052D导弹驱逐舰数量高达7艘，加上已经交付的两艘(172、173)和正在海试之中的两艘，从2010年开始短短5年之内，总共11艘052D服役、建造。大连造船厂还将建造一艘052D是已经定案的事情，这样，看得见的052D数量达到12艘，总吨位84000吨。");
		for (Map.Entry<BiLocNode, BiLocNode> entry : nodeMap.entrySet()) {
			   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}
	
	public static Map<BiLocNode, BiLocNode> resolveString(String str){
		List<String> locList=ParseLocRegex.parseString(str);
		return new Loc2XML().buildBiNodeMap(locList);
	}
	
	public static void resolveFile(){
		// TODO 从文件夹中读入文本，并生成同名的XML文件，其中包含了该文本文件中所包含的地名详情
		File[] files = dir.listFiles();
        try {
        	for(File file:files){
    			String name = file.getName();
                if(!name.endsWith(".txt")){
                    continue;
                }
                List<String> locList=ParseLocRegex.parseFile(file);
                new Loc2XML(file.getAbsolutePath()).buildXMLDoc(locList);
    		}
        	/*
        	Map<String,List<String>> resultMap=ParseLocLu.search(Loc2XML.locStrList, files.length);
        	for (Map.Entry<String, List<String>> entry : resultMap.entrySet()) {
        		new Loc2XML(dirPath+entry.getKey()).buildXMLDoc(entry.getValue());
        	}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
