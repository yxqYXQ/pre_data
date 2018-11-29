import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 * @author Yangxiaoqing
 * @ClassName: ReadTest
 * @Desciption: TODO
 * @date 2018/11/29 13:41
 * @Version 1.0
 */
public class ReadTest {
    public static void read(String path){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));//换成你的文件名
            String line = null;
            HashMap<HashMap<String, String>, Integer> map1 = new HashMap<>();//时间在0-6
            HashMap<HashMap<String, String>, Integer> map2 = new HashMap<>();//6-12
            HashMap<HashMap<String, String>, Integer> map3 = new HashMap<>();//12-18
            HashMap<HashMap<String, String>, Integer> map4 = new HashMap<>();//18-24
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String args[]){
    read("C:\\Users\\zp\\Desktop\\data_examples\\cert\\agg_feats\\cert_head.csv");
    }
}
