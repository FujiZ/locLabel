package locLabel;

import java.io.File;
import java.util.List;


public class LocLabel {
	public static final String dirPath="H:\\新建文件夹\\";//dir to be scanned
	public static final File dir=new File(dirPath);
	public static final File locFile=new File(LocLabel.class.getResource("/LocList.xml").getPath());
	
	public static void main(String[] args) {
		// TODO 从文件夹中读入文本，并生成同名的XML文件，其中包含了该文本文件中所包含的地名详情
        File[] files = dir.listFiles();
        try {
        	for(File file:files){
    			String name = file.getName();
                if(!name.endsWith(".txt")){
                    continue;
                }
                List<String> locList=ParseLocRegex.parse(file);
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
