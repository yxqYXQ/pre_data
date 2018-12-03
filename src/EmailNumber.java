import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Yangxiaoqing
 * @ClassName: EmailNumber
 * @Desciption: 不同时间段email情况
 * @date 2018/11/15 14:41
 * @Version 1.0
 */
public class EmailNumber {
    private static HashMap<String,List<String>> email_GSend = new HashMap<String,List<String>>();
    private static HashMap<String,List<String>> email_CommGSend = new HashMap<String,List<String>>();
    private static HashMap<String,List<String>> email_GView = new HashMap<String,List<String>>();
    private static HashMap<String,List<String>> email_CommGView = new HashMap<String,List<String>>();
    private static String fileNameNew ="";
    //find csv from folder
    public static void main(String[] args) {
        HashMap<Integer,HashMap<String,List<String>>> list =  search("emailCount/Email_GSend.csv","emailCount/Email_CommGSend.csv");
        email_GSend = list.get(1);
        email_CommGSend = list.get(2);
        HashMap<Integer,HashMap<String,List<String>>> list1 = search("emailCount/Email_GView.csv","emailCount/Email_CommGView.csv");
        email_GView= list1.get(1);
        email_CommGView = list1.get(2);
        walk("email/" ,"email1/");
//         String path1= "G:\\开题\\data_new\\email1\\";
//         HashMap<Integer,HashMap<String,List<String>>> list =  search("G:\\开题\\data_new\\Email_GSend.csv","G:\\开题\\data_new\\Email_CommGSend.csv");
//
//        email_GSend = list.get(1);
//        email_CommGSend = list.get(2);
//        HashMap<Integer,HashMap<String,List<String>>> list1 = search("G:\\开题\\data_new\\Email_GView.csv","G:\\开题\\data_new\\Email_CommGView.csv");
//        email_GView= list1.get(1);
//        email_CommGView = list1.get(2);
//        walk("G:\\开题\\data_new\\email\\" ,path1);

    }
    @SuppressWarnings("unchecked")
    public static HashMap<Integer,HashMap<String,List<String>>> search(String path,String path1){
        HashMap<Integer,HashMap<String,List<String>>> list  = new HashMap<Integer,HashMap<String,List<String>>>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            BufferedReader reader1 = new BufferedReader(new FileReader(path1));
            HashMap<String,List<String>> userGmap = new HashMap<String,List<String>>();
            HashMap<String,List<String>> userGCommmap = new HashMap<String,List<String>>();
            String line =null;
            String line1 = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                ArrayList<String> emailList = new ArrayList<String>();
                for (int i = 1; i <item.length ; i++) {
                    String str[] = item[i].split(";");
                    if(Integer.parseInt(str[1])>5) {
                        emailList.add(str[0]);
                    }
                }
                userGmap.put(item[0],emailList);//用户和文件对

            }
            while ((line1 = reader1.readLine()) != null) {
                String item[] = line1.split(",");
                ArrayList<String> emailList = new ArrayList<String>();
                for (int i = 1; i <item.length ; i++) {
                    String str[] = item[i].split(";");
                    if(Integer.parseInt(str[1])>5) {
                        emailList.add(str[0]);
                    }
                }
                userGCommmap.put(item[0],emailList);//用户和文件对
            }
            list.put(1,userGmap);
            list.put(2,userGCommmap);
        }catch (Exception e){
            System.out.println("错误1"+e.getMessage());
        }
        return  list;
    }
    public static void walk( String path ,String path1) {

        File root = new File( path );
        File[] list = root.listFiles();
        if (list == null) return;
        for ( File f : list ) {
            String filePath = f.getAbsolutePath();
            read(filePath,path1);
        }
    }
    public static void read(String path,String path1) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String headerMessage = readFirst(path);
            File tempFile = new File(path.trim());
            fileNameNew = tempFile.getName();
            String line = null;
            HashMap<HashMap<String, String>, Integer> map1 = new HashMap<>();
            int timecol;
            int activitycol;

            while ((line = reader.readLine()) != null) {
                String isAttr = "0";//默认为0，没有附件
                String ccIsComm ="1";//是公共
                String bccIsComm ="1";//是公共
                String fromIsComm ="1";
                String toIsComm = "1";
                String item[] = line.split(",");//一行数组
                activitycol =17;
                timecol = 11;
                String cc = item[14];
                String bcc = item[15];
                String from =item[16] ;
                String to = item[13];
                String activity = item[activitycol];//这里
                String time = item[timecol];//这里
                String userName = item[1];
                if(item.length==20){
                    isAttr="1";
                }

                HashMap<Integer,HashMap<String,List<String>>> maps = getLists(activity);
                ArrayList<String> list = (ArrayList<String>) maps.get(1).get(userName);
                if(!list.contains(from)){
                        fromIsComm = "0";
                }
                ArrayList<String> list1 = (ArrayList<String>) maps.get(2).get(userName);
                String tos[] = to.split(";");
                for (int i = 0; i < tos.length; i++) {
                    if(tos[i].length()!=0) {
                        if(!list1.contains(tos[i])){
                            toIsComm = "0";
                            break;
                        }
                    }
                }

                String ccs[] = cc.split(";");
                for (int i = 0; i < ccs.length; i++) {
                    if(ccs[i].length()!=0) {
                        if(!list1.contains(ccs[i])){
                            ccIsComm = "0";
                            break;
                        }
                    }
                }
                String bccs[] = bcc.split(";");
                for (int i = 0; i < bccs.length; i++) {
                    if(bccs[i].length()!=0) {
                        if(!list1.contains(bccs[i])){
                            bccIsComm = "0";
                            break;
                        }
                    }
                }

                String time1[] = time.split(" ");
                int flag = timePro(time);
                String times[] = {" 00:00:00-05:59:59", " 06:00:00-11:59:59", " 12:00:00-17:59:59", " 18:00:00-23:59:59"};
                HashMap<String, String> map = new HashMap<>();
                String key =toIsComm+","+ccIsComm+","+bccIsComm+","+fromIsComm+","+time1[0] + times[flag-1] + "," + isAttr;
                map.put(key, activity);
                if (map1.get(map) != null) {
                    int count = map1.get(map);
                    map1.put(map, count + 1);
                } else {
                    map1.put(map, 1);
                }
            }
            write(headerMessage, path1 + fileNameNew, map1);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //根据不同的操作，返回不同的list
    public static HashMap<Integer,HashMap<String,List<String>>> getLists(String activity){
        HashMap<Integer,HashMap<String,List<String>>> map = new HashMap<Integer,HashMap<String,List<String>>>();
        if(activity.equals("View")){
            map.put(1,email_GView);
            map.put(2,email_CommGView);
        }else{
            map.put(1,email_GSend);
            map.put(2,email_CommGSend);
        }
        return  map;
    }
    //写入表头
    public static void writeHeader(String path){
        try {
            File csv = new File(path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            String header = "role,projects,business_unit,functional_unit,department,team,supervisor,activity,toIsComm,ccIsComm,bccIsComm,fromIsComm,date,isAtt,count";
            bw.write(header);
            bw.newLine();
            bw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    //读取一行，获取用户的信息
    public static String  readFirst(String path){
        String userMesage = "";
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(path));//鎹㈡垚浣犵殑鏂囦欢鍚?
            String line1 = null;
            String strNew[] = null;
            while ((line1 = reader1.readLine()) != null) {
                strNew = line1.split(",");
                break;
            }
            for (int i = 3; i <10 ; i++) {
                userMesage += strNew[i]+",";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return userMesage;
    }
    public static void write(String headerMeaage,String path, HashMap<HashMap<String,String>,Integer> map1) {
        try {
            writeHeader(path);
            File csv = new File(path); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            for(HashMap<String,String> key:map1.keySet())
            {
                HashMap<String,String> map = key;
                int count = map1.get(map);
                for(String key1:map.keySet())
                {
                    String activity = map.get(key1);
                    String message = headerMeaage+activity+","+key1+","+count;
                    bw.write(message);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            // File对象的创建过程中的异常捕获
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
            } else return strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM == strDateEndM && strDateS <= strDateEndS;
        } else {
            return false;
        }
    }

}
