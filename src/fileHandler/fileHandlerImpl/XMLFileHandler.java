package fileHandler.fileHandlerImpl;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import collection.MaxSizeException;
import collection.MyCollection;
import fileHandler.MyFileHandler;
import model.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLFileHandler implements MyFileHandler, Runnable {

    private String fileName;
    private static Employee employee;
    private static MyCollection myCollection;

    public XMLFileHandler(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void read(String filePath) throws ParseException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileName);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("employee");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("First Name : "
                            + eElement
                            .getElementsByTagName("firstName")
                            .item(0)
                            .getTextContent());
                    System.out.println("Last Name : "
                            + eElement
                            .getElementsByTagName("lastName")
                            .item(0)
                            .getTextContent());
                    System.out.println("Date of  Birth : "
                            + eElement
                            .getElementsByTagName("dateOfBirth")
                            .item(0)
                            .getTextContent());
                    System.out.println("Experience : "
                            + eElement
                            .getElementsByTagName("experience")
                            .item(0)
                            .getTextContent());

                    employee.setFirstName(eElement.getElementsByTagName("firstName").item(0).getTextContent());
                    employee.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
                    employee.setDateOfBirth(Date.valueOf(eElement.getElementsByTagName("dateOfBirth").item(0).getTextContent()));
                    employee.setExperience(Double.valueOf(eElement.getElementsByTagName("experience").item(0).getTextContent()));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void write(String filePath) {
        try {
            employee = (Employee) myCollection.get();
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            // root element
            Element root = document.createElement("employees");
            document.appendChild(root);
            for(int i =0; i< 10; i++){
                // employee element
                Element employeeElement = document.createElement("employee");
                root.appendChild(employeeElement);
                // set an attribute to staff element
            /*
            Attr attr = document.createAttribute("id");
            attr.setValue("10");
            employee.setAttributeNode(attr);
            */
                //you can also use staff.setAttribute("id", "1") for this
                // firstname element
                Element firstName = document.createElement("firstName");
                firstName.appendChild(document.createTextNode(employee.getFirstName()));
                employeeElement.appendChild(firstName);
                // lastname element
                Element lastname = document.createElement("lastName");
                lastname.appendChild(document.createTextNode(employee.getLastName()));
                employeeElement.appendChild(lastname);
                // dateOfBirth element
                Element email = document.createElement("dateOfBirth");
                email.appendChild(document.createTextNode(String.valueOf(employee.getDateOfBirth())));
                employeeElement.appendChild(email);
                // experience elements
                Element department = document.createElement("experience");
                department.appendChild(document.createTextNode(String.valueOf(employee.getExperience())));
                employeeElement.appendChild(department);
            }
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (MaxSizeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        int xmlcounter = 0;
        if (xmlcounter < 100)
            if (name == "xmlRead") {
                try {
                    this.read(fileName);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (name == "xmlWrite") {
                this.write("/Users/muditjoshi/output.xml");
                xmlcounter++;
            }

    }


    /*public static void main(String[] args) {

        try {
            File inputFile = new File("/Users/rahuljain/Documents/XMLFileReader/src/input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("employee");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("First Name : "
                            + eElement
                            .getElementsByTagName("firstName")
                            .item(0)
                            .getTextContent());
                    System.out.println("Last Name : "
                            + eElement
                            .getElementsByTagName("lastName")
                            .item(0)
                            .getTextContent());
                    System.out.println("Date of  Birth : "
                            + eElement
                            .getElementsByTagName("dateOfBirth")
                            .item(0)
                            .getTextContent());
                    System.out.println("Experience : "
                            + eElement
                            .getElementsByTagName("experience")
                            .item(0)
                            .getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
