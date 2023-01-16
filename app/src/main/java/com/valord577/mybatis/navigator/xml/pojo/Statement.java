package com.valord577.mybatis.navigator.xml.pojo;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * <p>Mapping for MyBatis XML Tags ["select", "insert", "update", "delete"]</p>
 *
 * @author valor.
 */
public interface Statement extends DomElement {

    @Attribute("id")
    GenericAttributeValue<String> getId();
}
