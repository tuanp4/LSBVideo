/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package video_steganography;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JOptionPane;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author khact
 */
public class Chart{

    public Chart(File f1,File f2) throws IOException {
        byte [] input=Files.readAllBytes(f1.toPath());
        byte [] output=Files.readAllBytes(f2.toPath());
        if (input.length!=output.length) throw new IOException("Kích thước 2 file không giống nhau");
        int [] in= new int[256];
        int [] out= new int[256];
        int size = input.length;
        for (int i = 0; i < size; i++) {
            in[input[i]&0xFF]++; 
            out[output[i]&0xFF]++; 
        }
        System.out.println("");
        int dem=0;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String name1 = f1.getName();
        String name2 = f2.getName();
        for (int i = 0; i < 256; i++) {
            if (in[i]!= out[i]){
                dem++;
                dataset.setValue(in[i], name1, "" + i);
                dataset.setValue(out[i], name2, "" + i);
            }
        }
        JFreeChart chart = ChartFactory.createBarChart("Biểu đồ sai khác về tần suất", "Các giá trị byte có sự xuất hiện khác nhau", "Tần suất xuất hiện (lần)", dataset);
        ChartFrame cf = new ChartFrame("Biểu đồ phân tích", chart, true);
        cf.setSize(1200, 700);
        cf.setVisible(true);
        cf.setDefaultCloseOperation(1);
        cf.setLocationRelativeTo(null);
        if (dem==0) {
            JOptionPane.showMessageDialog(null, "Không phát hiện thấy giấu tin");
        }
    }
}
