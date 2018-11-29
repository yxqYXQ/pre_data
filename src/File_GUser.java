import java.io.*;
import java.util.*;

/**
 * @author Yangxiaoqing
 * @ClassName: File_GUser
 * @Desciption: 统计用户访问文件情况
 * @date 2018/11/28 14:20
 * @Version 1.0
 */
public class File_GUser {
    private static HashMap<String, HashMap<String,Integer>> userMap = new HashMap<String, HashMap<String,Integer>>();//参数分别为：用户名、file的名字、对应的访问次数
    private static ArrayList<String> list = new ArrayList<String>();
    public static void main(String args[]){
//         readUser("");
//         readFile("");
//         writeFile("UserG_file.csv");
        readUser("G:\\开题\\用户行为\\数据集\\r6.2\\LDAP\\");
        readFile("G:\\开题\\用户行为\\数据集\\r6.2\\");
        writeFile("G:\\开题\\data_new\\UserG_file.csv");
    }

    //确保是2009-12的员工
    public static void readUser(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"2009-12.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                list.add(item[1]);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    //读用户所有的文件操作记录，并统计每个用户使用文件的次数
    public static void readFile(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"file.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                String fileName = item[4];//文件名
                String userName = item[2];//用户id
                String pc = item[3];
                HashMap<String,Integer> newMap = new HashMap<String,Integer>();//参数分别为：file名字、对应的访问次数
                //判断是否是2009-12的员工
                if(list.contains(userName)){
                    //判断是否map中是否以已经存放了该用户
                    if(userMap.get(userName)!=null){
                        newMap = userMap.get(userName);
                        //判断pc-文件是否已经存在
                        if( newMap.get(pc+"_"+fileName)!=null) {
                            int count = newMap.get(pc+"_"+fileName);
                            count = count + 1;
                            newMap.put(pc+"_"+fileName,count);
                        }else{
                            newMap.put(pc+"_"+fileName,1);
                        }
                    }else{
                        newMap.put(pc+"_"+fileName,1);
                    }
                    userMap.put(userName,newMap);
                }

            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    //写入每个用户使用文件的次数
    public static void writeFile(String path){
        File csv = new File(path); // CSV数据文件
        BufferedWriter bw = null; // 附加
        try {
            bw = new BufferedWriter(new FileWriter(csv, true));
            Iterator it = userMap.entrySet().iterator();
            int total = 0;
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
                    Integer value = mapping.getValue();
                    pcNames += key1 + ";" + value + ",";
                    total +=value;
                }
                String  pc  = pcNames.substring(0, pcNames.length() - 1);

                bw.write(key + "," + pc);
                bw.newLine();
            }
            System.out.println("total"+total);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
