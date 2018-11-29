import java.io.*;
import java.util.*;

/**
 * @author Yangxiaoqing
 * @ClassName: PC_GUser
 * @Desciption:统计每个用户的一对一PC
 * @date 2018/11/18 15:27
 * @Version 1.0
 */
public class PC_User {
    static HashMap<String, HashMap<String,Integer>> userMap = new HashMap<>();
    @SuppressWarnings("unchecked")
    public static void main(String args[]) {
      //walk("G:\\开题\\data_new\\logon");
     walk("logon");
        //File csv = new File("G:\\开题\\data_new\\User_PC.csv"); // CSV数据文件
       File csv = new File("User_PC.csv"); // CSV数据文件
        BufferedWriter bw = null; // 附加
        try {
            bw = new BufferedWriter(new FileWriter(csv, true));
           Iterator it = userMap.entrySet().iterator();

      while(it.hasNext()){
          Map.Entry entry = (Map.Entry) it.next();
          String key = (String)entry.getKey();
          HashMap<String,Integer> map1 = (HashMap<String,Integer>) entry.getValue();
          List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map1.entrySet());
          Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
              // 降序排序
              public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                  return -o1.getValue().compareTo(o2.getValue());
              }
          });
          String pcNames="";
          for (Map.Entry<String, Integer> mapping : list) {
                  String key1 = mapping.getKey();
                 // Integer value = mapping.getValue();
                  pcNames = key1;
                  break;
          }
          //   String  pc  = pcNames.substring(0, pcNames.length() - 1);

          bw.write(key + "," + pcNames);
          bw.newLine();
      }

        bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public static void walk(String path) {

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {

           String filePath = f.getAbsolutePath();
            read(filePath);
        }
    }
    @SuppressWarnings("unchecked")
    public static void read(String path) {
        try{
        BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
        String fileName[] = {"device","file","email","http"};
        //reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
        String line = null;

        File tempFile =new File( path.trim());
        String fileName1 = tempFile.getName();
        String fileName2[] = fileName1.split("_");
        HashMap<String,Integer> map = new HashMap<>();

        while ((line = reader.readLine()) != null) {
            String item[] = line.split(",");//一行数组
            String PC = item[12];
            if(userMap.get(fileName2[1])!=null){
                map = userMap.get(fileName2[1]);
                if(map.get(PC)!=null){
                    int count = map.get(PC)+1;
                    map.put(PC,count);
                }else{
                    map.put(PC,1);
                }

            }else{
               map.put(PC,1);
            }
            userMap.put(fileName2[1],map);
            }
            for (int  j= 0; j <fileName.length ; j++) {
                String newName = "";
                for (int i = 0; i < fileName2.length; i++) {
                    if(i==0){
                        fileName2[i] =fileName[j];

                    }
                    newName += fileName2[i]+"_";
                }
                newName = newName.substring(0,newName.length()-1);
              // File dir = new File("G:\\开题\\data_new\\" + fileName[j] + "\\" + newName);

               File dir = new File(fileName[j] + "/" + newName);
                if(dir.exists()) {
                  BufferedReader reader1 = new BufferedReader(new FileReader( fileName[j] + "/" + newName));//换成你的文件名
                   //  BufferedReader reader1 = new BufferedReader(new FileReader("G:\\开题\\data_new\\" + fileName[j] + "\\" + newName));//换成你的文件名
                    while ((line = reader1.readLine()) != null) {
                        String item[] = line.split(",");//一行数组
                        String PC = item[12];
                        if(userMap.get(fileName2[1])!=null){
                            map = userMap.get(fileName2[1]);
                            if(map.get(PC)!=null){
                                int count = map.get(PC)+1;
                                map.put(PC,count);
                            }else{
                                map.put(PC,1);
                            }

                        }else{
                            map.put(PC,1);
                        }
                        userMap.put(fileName2[1],map);
                    }
                }
            }
        } catch(
        Exception e)

        {
            e.printStackTrace();
        }
    }
}
