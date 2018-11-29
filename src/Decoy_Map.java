import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import static jdk.nashorn.internal.objects.NativeDebug.map;

/**
 * @author Yangxiaoqing
 * @ClassName: Decoy_Map
 * @Desciption: TODO
 * @date 2018/11/19 14:13
 * @Version 1.0
 */
public class Decoy_Map {
    public static void main(String args[]) {
        HashMap<String, String> map = read();
    }

    public static HashMap<String, String> read() {
        HashMap<String, String> map = new HashMap<>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader("G:\\开题\\用户行为\\数据集\\r6.2\\decoy_file.csv"));//换成你的文件名

            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;

            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//一行数组
                String pc = item[1];
                String dir = item[0];
                String str = pc+","+dir;
                map.put(str, pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map.size());
        return map;
    }
}
