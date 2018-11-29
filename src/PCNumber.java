import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Yangxiaoqing
 * @ClassName: PC_GUser
 * @Desciption:统计不同时间段用户使用PC机、公共的和非公共的
 * @date 2018/11/18 15:27
 * @Version 1.0
 */
public class PCNumber {
    private static String fileNew ="";
    @SuppressWarnings("unchecked")
    public static void main(String args[]) {

        //walk("G:\\开题\\data_new\\logon");
        walk("logon");
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
    public static void write(String userInfo,String pathName, HashMap<HashMap<String,String>,Integer> map1,HashMap<HashMap<String,String>,Integer> map2,HashMap<HashMap<String,String>,Integer> map3,HashMap<HashMap<String,String>,Integer> map4) {
        try {

            String pathnew[] = pathName.split("_");
            String path ="";
            for (int h = 0; h < pathnew.length; h++) {
                if(h==0){
                    pathnew[h] = "pc_use";
                }
                path += pathnew[h]+"_";
            }
            path = path.substring(0,path.length()-1);
            File csv = new File("pc_use1/" + path); // CSV数据文件
           // File csv = new File("G:\\开题\\data_new\\pc_use1\\" + path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            for(HashMap<String,String> key:map1.keySet())
            {
                HashMap<String,String> map = key;
                int count = map1.get(map);
                for(String key1:map.keySet())
                {
                    String activity = map.get(key1);
                    String str =userInfo+","+key1+","+activity+","+count;
                    bw.write(str);
                    bw.newLine();
                }
            }
            for(HashMap<String,String> key:map2.keySet())
            {
                HashMap<String,String> map = key;
                int count = map2.get(map);
                for(String key1:map.keySet())
                {
                    String activity = map.get(key1);
                    String str =userInfo+","+key1+","+activity+","+count;
                    bw.write(str);
                    bw.newLine();
                }
            }
            for(HashMap<String,String> key:map3.keySet())
            {
                HashMap<String,String> map = key;
                int count = map3.get(map);
                for(String key1:map.keySet())
                {
                    String activity = map.get(key1);
                    String str =userInfo+","+key1+","+activity+","+count;
                    bw.write(str);
                    bw.newLine();
                }
            }
            for(HashMap<String,String> key:map4.keySet())
            {
                HashMap<String,String> map = key;
                int count = map4.get(map);
                for(String key1:map.keySet())
                {
                    String activity = map.get(key1);
                    String str =userInfo+","+key1+","+activity+","+count;
                    bw.write(str);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public static void read(String path) {
        try{
        HashMap<HashMap<String,String>,Integer> map1 = new HashMap<>();//时间在0-6
        HashMap<HashMap<String,String>,Integer> map2 = new HashMap<>();//6-12
             HashMap<HashMap<String,String>,Integer> map3 = new HashMap<>();//12-18
        HashMap<HashMap<String,String>,Integer> map4 = new HashMap<>();//18-24
        BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
        String fileName[] = {"device","file","email","http"};
        //reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            BufferedReader reader2 = new BufferedReader(new FileReader(path));//换成你的文件名

        String line = null;
        String line1 = null;
        String userInfo="";
        while ((line1 = reader2.readLine()) != null) {
            String[] strNew = line1.split(",");//一行数组
            for (int k = 3; k <10 ; k++) {
                userInfo+=strNew[k]+",";
            }
            userInfo = userInfo.substring(0,userInfo.length()-1);
            break;
        }
        File tempFile =new File( path.trim());
        String fileName1 = tempFile.getName();
        fileNew =fileName1;
        String fileName2[] = fileName1.split("_");
        int  activitycol =0;
        int timecol =0;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//一行数组
                activitycol = item.length-2;
                timecol = item.length-3;
                String activity = item[activitycol];//这里
                String time = item[timecol];//这里
                String time1[] = time.split(" ");
                int flag = timePro(time);
                if(flag==1){
                    HashMap<String,String> map = new HashMap<>();
                    map.put(time1[0]+" 00:00:00-05:59:59",activity);
                    if(map1.get(map)!=null){
                        int count = map1.get(map);
                        map1.put(map,count+1);
                    }else{
                        map1.put(map,1);
                    }
                }
                if(flag==2){
                    HashMap<String,String> map = new HashMap<>();
                    map.put(time1[0]+" 06:00:00-11:59:59",activity);
                    if(map2.get(map)!=null){
                        int count = map2.get(map);
                        map2.put(map,count+1);
                    }else{
                        map2.put(map,1);
                    }
                }
                if(flag==3){
                    HashMap<String,String> map = new HashMap<>();
                    map.put(time1[0]+" 12:00:00-17:59:59",activity);
                    if(map3.get(map)!=null){
                        int count = map3.get(map);
                        map3.put(map,count+1);
                    }else{
                        map3.put(map,1);
                    }
                }
                if(flag==4){
                    HashMap<String,String> map = new HashMap<>();
                    map.put(time1[0]+" 18:00:00-23:59:59",activity);
                    if(map4.get(map)!=null){
                        int count = map4.get(map);
                        map4.put(map,count+1);
                    }else{
                        map4.put(map,1);
                    }
                }
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
                        if(fileName[j].equals("file")){
                            activitycol = item.length-6;
                            timecol = item.length-7;
                        }else if(fileName[j].equals("http")){
                            activitycol = item.length-3;
                            timecol = item.length-4;
                        }else if(fileName[j].equals("device")){
                            activitycol = item.length-3;
                            timecol = item.length-4;
                        }else if(fileName[j].equals("email")){
                            activitycol = item.length-7;
                            timecol = item.length-8;
                        }
                        String activity = item[activitycol];//这里
                        String time = item[timecol];//这里
                        String time1[] = time.split(" ");
                        System.out.println(fileName[j]);
                        System.out.println(time);
                        int flag = timePro(time);
                        if(flag==1){
                            HashMap<String,String> map = new HashMap<>();
                            map.put(time1[0]+" 00:00:00-05:59:59",activity);
                            if(map1.get(map)!=null){
                                int count = map1.get(map);
                                map1.put(map,count+1);
                            }else{
                                map1.put(map,1);
                            }
                        }
                        if(flag==2){
                            HashMap<String,String> map = new HashMap<>();
                            map.put(time1[0]+" 06:00:00-11:59:59",activity);
                            if(map2.get(map)!=null){
                                int count = map2.get(map);
                                map2.put(map,count+1);
                            }else{
                                map2.put(map,1);
                            }
                        }
                        if(flag==3){
                            HashMap<String,String> map = new HashMap<>();
                            map.put(time1[0]+" 12:00:00-17:59:59",activity);
                            if(map3.get(map)!=null){
                                int count = map3.get(map);
                                map3.put(map,count+1);
                            }else{
                                map3.put(map,1);
                            }
                        }
                        if(flag==4){
                            HashMap<String,String> map = new HashMap<>();
                            map.put(time1[0]+" 18:00:00-23:59:59",activity);
                            if(map4.get(map)!=null){
                                int count = map4.get(map);
                                map4.put(map,count+1);
                            }else{
                                map4.put(map,1);
                            }
                        }

                    }
                }

            }
            write(userInfo, fileNew ,map1,map2,map3,map4);
            //写入：

        } catch(
        Exception e)

        {
            e.printStackTrace();
        }
    }
    public static int timePro(String time){
        int flag =0;
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time1[] = time.split(" ");
        String time2[] = time1[0].split("/");
        String newTime = time2[2]+"-"+time2[1]+"-"+time2[0];
        String date = newTime+" "+time1[1];
        if(isInDate(date,"00:00:00","05:59:59")){
            flag =1;
        }
        if(isInDate(date,"06:00:00","11:59:59")){
            flag =2;
        }
        if(isInDate(date,"12:00:00","17:59:59")){
            flag =3;
        }
        if(isInDate(date,"18:00:00","23:59:59")){
            flag =4;
        }
        return flag;
    }


    public static boolean isInDate(String strDate, String strDateBegin,
                                   String strDateEnd) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String strDate = sdf.format(date);
        // 截取当前时间时分秒
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        // 截取开始时间时分秒
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
        // 截取结束时间时分秒
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
        if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
            // 当前时间小时数在开始时间和结束时间小时数之间
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM
                    && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                return true;
            }
            // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
            else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
            } else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM == strDateEndM && strDateS <= strDateEndS) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
