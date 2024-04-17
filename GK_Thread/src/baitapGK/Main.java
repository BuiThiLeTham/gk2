package baitapGK;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import java.security.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class SinhVien {
    int maSV;
    String ten;
    String diaChi;
    String ngaySinh;
    int tuoi;

    public SinhVien(int maSV, String ten, String diaChi, String ngaySinh) {
        this.maSV = maSV;
        this.ten = ten;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
    }
    public String getInfo() {
        return "Mã SV: " + maSV + ", Tên: " + ten + ", Địa chỉ: " + diaChi + ", Tuổi: " + tuoi;
    }
}

class Luong1 implements Runnable {
    List<SinhVien> sinhViens;

    public Luong1(List<SinhVien> sinhViens) {
        this.sinhViens = sinhViens;
    }

    public void run() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("./src/baitapGK/student.xml");

            NodeList studentList = doc.getElementsByTagName("student");
            for (int i = 0; i < studentList.getLength(); i++) {
                Node studentNode = studentList.item(i);
                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) studentNode;

                    int maSV = Integer.parseInt(studentElement.getElementsByTagName("id").item(0).getTextContent());
                    String ten = studentElement.getElementsByTagName("name").item(0).getTextContent();
                    String diaChi = studentElement.getElementsByTagName("address").item(0).getTextContent();
                    String ngaySinh = studentElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();
                    sinhViens.add(new SinhVien(maSV, ten, diaChi, ngaySinh));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Luong2 implements Runnable {
    List<SinhVien> sinhViens;

    public Luong2(List<SinhVien> sinhViens) {
        this.sinhViens = sinhViens;
    }

    public void run() {
        for (SinhVien sinhVien : sinhViens) {
            Calendar ngaySinh = Calendar.getInstance();
            try {
                ngaySinh.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(sinhVien.ngaySinh));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int tuoi = Calendar.getInstance().get(Calendar.YEAR) - ngaySinh.get(Calendar.YEAR);
            sinhVien.tuoi = tuoi;
        }
    }
}

class Luong3 implements Runnable {
    List<SinhVien> sinhViens;

    public Luong3(List<SinhVien> sinhViens) {
        this.sinhViens = sinhViens;
    }

    public void run() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("students");
            doc.appendChild(root);

            for (SinhVien sinhVien : sinhViens) {
                Element studentElement = doc.createElement("student");
                root.appendChild(studentElement);

                Element idElement = doc.createElement("id");
                idElement.setTextContent(Integer.toString(sinhVien.maSV));
                studentElement.appendChild(idElement);

                Element nameElement = doc.createElement("name");
                nameElement.setTextContent(sinhVien.ten);
                studentElement.appendChild(nameElement);

                Element addressElement = doc.createElement("address");
                addressElement.setTextContent(sinhVien.diaChi);
                studentElement.appendChild(addressElement);

                Element ageElement = doc.createElement("age");
                ageElement.setTextContent(Integer.toString(sinhVien.tuoi));
                studentElement.appendChild(ageElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("./src/baitapGK/kq.xml"));

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<SinhVien> sinhViens = new ArrayList<>();
        Thread t1 = new Thread(new Luong1(sinhViens));
        Thread t2 = new Thread(new Luong2(sinhViens));
        Thread t3 = new Thread(new Luong3(sinhViens));

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t3.start();
        try {
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (SinhVien sinhVien : sinhViens) {
            System.out.println(sinhVien.getInfo());
        }
    }
}
