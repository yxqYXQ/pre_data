import java.io.*;
import java.util.*;

/**
 * @author Yangxiaoqing
 * @ClassName: File_GUser
 * @Desciption: 统计用户邮箱使用情况
 * @date 2018/11/28 14:20
 * @Version 1.0
 */
public class Email_GUser {
    private static HashMap<String, HashMap<String,Integer>> map1 = new HashMap<String, HashMap<String,Integer>>();//用户发送的邮箱/
    private static HashMap<String, HashMap<String,Integer>> map2 = new HashMap<String, HashMap<String,Integer>>();//用户自己使用的邮箱
    private static HashMap<String, HashMap<String,Integer>> map11 = new HashMap<>();//用户自己使用的邮箱
    private static HashMap<String, HashMap<String,Integer>> map22 = new HashMap<String, HashMap<String,Integer>>();//用户自己使用的邮箱
    private static ArrayList<String> list = new ArrayList<String>();
    @SuppressWarnings("unchecked")
    public static void main(String args[]){
//         readUser("");
//         readFile("");
//         writeFile("emailCount/Email_GSend.csv",map2);
//         writeFile("emailCount/Email_CommGSend.csv",map1);
//        writeFile("emailCount/Email_GView.csv",map22);
//        writeFile("emailCount/Email_CommGView.csv",map11);

        readUser("G:\\开题\\用户行为\\数据集\\r6.2\\LDAP\\");
        readFile("G:\\开题\\用户行为\\数据集\\r6.2\\");
        writeFile("G:\\开题\\data_new\\Email_GSend.csv",map2);
        writeFile("G:\\开题\\data_new\\Email_CommGSend.csv",map1);
        writeFile("G:\\开题\\data_new\\Email_GView.csv",map22);
        writeFile("G:\\开题\\data_new\\Email_CommGView.csv",map11);
    }

    //确保是2009-12的员工
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public static void readFile(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+"email.csv"));//换成你的文件名
          // reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                String activity = item[8];
                String userName = item[2];//用户id
                ArrayList<String> emailNamelist = new ArrayList<String>();
                String  emailNameNew = item[7];
                if (item[4].length() != 0) {
                    String tos[] = item[4].split(";");
                    for (int i = 0; i < tos.length; i++) {
                        if(tos[i].length()!=0) {
                            emailNamelist.add(tos[i]);
                        }
                    }
                }
                if (item[5].length() != 0) {
                    String ccs[] = item[5].split(";");
                    for (int i = 0; i < ccs.length; i++) {
                        if(ccs[i].length()!=0) {
                            emailNamelist.add(ccs[i]);
                        }
                    }
                }
                if (item[6].length() != 0) {
                    String bccs[] = item[6].split(";");
                    for (int i = 0; i < bccs.length; i++) {
                        if(bccs[i].length()!=0) {
                            emailNamelist.add(bccs[i]);
                        }
                    }
                }
                    //判断是否是2009-12的员工
                    if (list.contains(userName)) {
                        //用户自己使用的邮箱情况
                        if (activity.equals("Send")) {
                            //查看时，统计fromEmail
                            //接收邮箱
                            for (String value : emailNamelist) {
                                //判断是否map中是否以已经存放了该用户
                                HashMap<String, Integer> newMap = new HashMap<String, Integer>();
                                if (map1.get(userName) != null) {
                                    newMap = map1.get(userName );
                                    //判断pc-文件是否已经存在
                                    if (newMap.get(value) != null) {
                                        int count = newMap.get(value);
                                        count = count + 1;
                                        newMap.put(value, count);
                                    } else {
                                        newMap.put(value, 1);
                                    }
                                } else {
                                    newMap.put(value, 1);
                                }
                                map1.put(userName, newMap);
                            }
                            if (emailNameNew.length() != 0) {
                                HashMap<String, Integer> newMap = new HashMap<String, Integer>();
                                if (map2.get(userName) != null) {
                                    newMap = map2.get(userName);
                                    //判断pc-文件是否已经存在
                                    if (newMap.get(emailNameNew) != null) {
                                        int count = newMap.get(emailNameNew);
                                        count = count + 1;
                                        newMap.put(emailNameNew, count);
                                    } else {
                                        newMap.put(emailNameNew, 1);
                                    }
                                } else {
                                    newMap.put(emailNameNew, 1);
                                }
                                map2.put(userName, newMap);
                            }
                        }else{
                            //查看时，统计fromEmail
                            //接收邮箱
                            for (String value : emailNamelist) {
                                //判断是否map中是否以已经存放了该用户
                                HashMap<String, Integer> newMap = new HashMap<String, Integer>();
                                if (map11.get(userName) != null) {
                                    newMap = map11.get(userName );
                                    //判断pc-文件是否已经存在

                                    if (newMap.get(value) != null) {
                                        int count = newMap.get(value);
                                        count = count + 1;
                                        newMap.put(value, count);
                                    } else {
                                        newMap.put(value, 1);
                                    }
                                } else {
                                    newMap.put(value, 1);
                                }
                                map11.put(userName, newMap);
                            }
                            //发送邮箱
                            if (emailNameNew.length() != 0) {
                                HashMap<String, Integer> newMap = new HashMap<String, Integer>();
                                if (map22.get(userName) != null) {
                                    newMap = map22.get(userName);
                                    //判断pc-文件是否已经存在
                                    if (newMap.get(emailNameNew) != null) {
                                        int count = newMap.get(emailNameNew);
                                        count = count + 1;
                                        newMap.put(emailNameNew, count);
                                    } else {
                                        newMap.put(emailNameNew, 1);
                                    }
                                } else {
                                    newMap.put(emailNameNew, 1);
                                }
                                map22.put(userName, newMap);
                            }
                        }
                    }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    //写入每个用户使用文件的次数
    @SuppressWarnings("unchecked")
    public static void writeFile(String path,HashMap<String, HashMap<String,Integer>> userMap){
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
