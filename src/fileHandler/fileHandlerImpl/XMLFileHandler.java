package fileHandler.fileHandlerImpl;

import java.io.File;
import java.text.SimpleDateFormat;
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
    private MyCollection myCollection;

    public XMLFileHandler(String fileName, MyCollection myCollection) {
        this.fileName = fileName;
        this.myCollection = myCollection;
    }

    @Override
    public MyCollection read(String filePath) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileName);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("employee");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Employee employee = new Employee();
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    employee.setFirstName(eElement.getElementsByTagName("firstName").item(0).getTextContent());
                    employee.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
                    java.util.Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(eElement.getElementsByTagName("dateOfBirth").item(0).getTextContent());
                    employee.setDateOfBirth(dateOfBirth);
                    employee.setExperience(Double.valueOf(eElement.getElementsByTagName("experience").item(0).getTextContent()));
                    myCollection.add(employee);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (MaxSizeException e) {
            e.printStackTrace();
        }
        return myCollection;

    }

    @Override
    public void write(String filePath) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("employees");
            document.appendChild(root);
            for(int i=0;i<100;i++) {
                Employee employee = (Employee) myCollection.get();
                Element employeeElement = document.createElement("employee");
                root.appendChild(employeeElement);
                Element firstName = document.createElement("firstName");
                firstName.appendChild(document.createTextNode(employee.getFirstName()));
                employeeElement.appendChild(firstName);
                Element lastname = document.createElement("lastName");
                lastname.appendChild(document.createTextNode(employee.getLastName()));
                employeeElement.appendChild(lastname);
                Element email = document.createElement("dateOfBirth");
                email.appendChild(document.createTextNode(String.valueOf(employee.getDateOfBirth())));
                employeeElement.appendChild(email);
                Element department = document.createElement("experience");
                department.appendChild(document.createTextNode(String.valueOf(employee.getExperience())));
                employeeElement.appendChild(department);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);
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
        if (name == "xmlRead") {
            this.read(fileName);
        } else if (name == "xmlWrite") {
            this.write("/Users/muditjoshi/output.xml");
            xmlcounter++;
        }

        System.out.println("XML: "+ xmlcounter);

    }
}
