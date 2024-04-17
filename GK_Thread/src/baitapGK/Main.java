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

class Student {
    int id;
    String name;
    String address;
    String dateOfBirth;
    int age;

    public Student(int id, String name, String address, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
    public String getInfo() {
        return "ID: " + id + ", Name: " + name + ", Address: " + address + ", Age: " + age;
    }
}

class Thread1 implements Runnable {
    List<Student> students;

    public Thread1(List<Student> students) {
        this.students = students;
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

                    int id = Integer.parseInt(studentElement.getElementsByTagName("id").item(0).getTextContent());
                    String name = studentElement.getElementsByTagName("name").item(0).getTextContent();
                    String address = studentElement.getElementsByTagName("address").item(0).getTextContent();
                    String dateOfBirth = studentElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();
                    students.add(new Student(id, name, address, dateOfBirth));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Thread2 implements Runnable {
    List<Student> students;

    public Thread2(List<Student> students) {
        this.students = students;
    }

    public void run() {
        for (Student student : students) {
            Calendar dob = Calendar.getInstance();
            try {
                dob.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(student.dateOfBirth));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int age = Calendar.getInstance().get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            student.age = age;
        }
    }
}

class Thread3 implements Runnable {
    List<Student> students;

    public Thread3(List<Student> students) {
        this.students = students;
    }

    public void run() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("students");
            doc.appendChild(root);

            for (Student student : students) {
                Element studentElement = doc.createElement("student");
                root.appendChild(studentElement);

                Element idElement = doc.createElement("id");
                idElement.setTextContent(Integer.toString(student.id));
                studentElement.appendChild(idElement);

                Element nameElement = doc.createElement("name");
                nameElement.setTextContent(student.name);
                studentElement.appendChild(nameElement);

                Element addressElement = doc.createElement("address");
                addressElement.setTextContent(student.address);
                studentElement.appendChild(addressElement);

                Element ageElement = doc.createElement("age");
                ageElement.setTextContent(Integer.toString(student.age));
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
        List<Student> students = new ArrayList<>();
        Thread t1 = new Thread(new Thread1(students));
        Thread t2 = new Thread(new Thread2(students));
        Thread t3 = new Thread(new Thread3(students));

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


        for (Student student : students) {
            System.out.println(student.getInfo());
        }
    }
}
