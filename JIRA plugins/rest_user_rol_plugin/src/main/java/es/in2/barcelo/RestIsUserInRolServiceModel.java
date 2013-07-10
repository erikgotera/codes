package es.in2.barcelo;

import javax.xml.bind.annotation.*;
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestIsUserInRolServiceModel {

    @XmlAttribute
    private String respuesta;


    public RestIsUserInRolServiceModel() {
    }

    public RestIsUserInRolServiceModel(String respuesta) {
        this.respuesta = respuesta;
    }
 
    public String getRespuesta() {
        return respuesta;
    }
 
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
