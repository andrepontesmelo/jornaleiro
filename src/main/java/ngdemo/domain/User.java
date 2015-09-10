package ngdemo.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// from http://www.vogella.com/articles/REST/
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class User {

    private String firstName;
    private String lastName;
    private String myVar;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMyVar() {
        return myVar;
    }

    public void setMyVar(String myVar) {
        this.myVar = myVar;
    }
}
