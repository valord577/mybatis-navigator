package com.valord577.mybatis.navigator.xml;

import com.intellij.openapi.module.Module;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomFileDescription;
import com.valord577.mybatis.navigator.xml.pojo.Mapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>Allow mybatis xml files to be indexed</p>
 * <see>
 *   <a href="https://plugins.jetbrains.com/docs/intellij/xml-dom-api.html">
 *   Intellij XML DOM API
 *   </a>
 * </see>
 *
 * @author valor.
 */
public class MybatisXmlFileDescription extends DomFileDescription<Mapper> {

    public MybatisXmlFileDescription() {
        super(Mapper.class, Tag.MAPPER.getValue());
    }

    @Override
    public boolean isMyFile(@NotNull XmlFile file, @Nullable Module module) {
        XmlTag rootTag = file.getRootTag();
        return null != rootTag && super.getRootTagName().equals(rootTag.getName());
    }

    public enum Tag {
        MAPPER("mapper", "namespace"),
        SELECT("select", "id"),
        INSERT("insert", "id"),
        UPDATE("update", "id"),
        DELETE("delete", "id");

        private final String value;

        private final String major;

        Tag(String value, String major) {
            this.value = value;
            this.major = major;
        }

        public static boolean isTag(String v, String m) {
            Tag tag;
            try {
                tag = Tag.valueOf(v.toUpperCase());
            } catch (Throwable e) {
                return false;
            }

            return tag.major.equals(m);
        }

        public String getValue() {
            return this.value;
        }

        public String getMajor() {
            return this.major;
        }
    }
}
