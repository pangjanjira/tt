//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2.1.1-4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2556.02.18 at 03:59:19 ��ѧ���§ ICT 
//


package th.co.scb.model.customs.g1c054;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NamePrefix1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NamePrefix1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DOCT"/>
 *     &lt;enumeration value="MIST"/>
 *     &lt;enumeration value="MISS"/>
 *     &lt;enumeration value="MADM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NamePrefix1Code")
@XmlEnum
public enum NamePrefix1Code {

    DOCT,
    MIST,
    MISS,
    MADM;

    public String value() {
        return name();
    }

    public static NamePrefix1Code fromValue(String v) {
        return valueOf(v);
    }

}
