package com.valord577.mybatis.navigator.xml.pojo;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagsList;

import java.util.List;

/**
 * <p>Mapping for MyBatis XML File</p>
 *
 * @author valor.
 */
public interface Mapper extends DomElement {

    @Attribute("namespace")
    GenericAttributeValue<String> getNamespace();

    @SubTagsList({"select", "insert", "update", "delete"})
    List<Statement> getStatements();

    List<Select> getSelects();

    List<Insert> getInserts();

    List<Update> getUpdates();

    List<Delete> getDeletes();
}
