package com.valord577.mybatis.navigator;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomService;
import com.intellij.util.xml.GenericAttributeValue;
import com.valord577.mybatis.navigator.kit.Resources;
import com.valord577.mybatis.navigator.kit.Strings;
import com.valord577.mybatis.navigator.xml.pojo.Mapper;
import com.valord577.mybatis.navigator.xml.pojo.Statement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Navigate to XML from JAVA Mapper.</p>
 *
 * @author valor.
 * -- 2020/4/22 14:07
 */
public class JavaLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        // whether Psi is a java interface file
        if (!(element instanceof PsiIdentifier)) {
            return;
        }
        PsiElement parent = element.getParent();
        if (null == parent) {
            return;
        }
        // jif -> Java Interface File
        PsiClass jif = this.getInterface(parent);
        if (null == jif) {
            return;
        }

        // this project
        final Project project = element.getProject();
        // package
        final String qualifiedName = jif.getQualifiedName();

        // search xml files
        DomService domService = DomService.getInstance();
        GlobalSearchScope scope = GlobalSearchScope.projectScope(project);
        List<DomFileElement<Mapper>> xmlFiles = domService.getFileElements(Mapper.class, project, scope);
        if (null == xmlFiles || xmlFiles.isEmpty()) {
            return;
        }
        // filter
        xmlFiles.removeIf(e -> {
            Mapper mapper = e.getRootElement();

            // no namespace defined
            GenericAttributeValue<String> namespace = mapper.getNamespace();
            if (null == namespace) {
                return true;
            }
            // blank namespace
            String namespaceStr = namespace.getRawText();
            if (Strings.isBlank(namespaceStr)) {
                return true;
            }

            return !namespaceStr.equals(qualifiedName);
        });

        // collect xml tags
        if (xmlFiles.isEmpty()) {
            return;
        }
        // 16 statements per xml file
        int capacity = xmlFiles.size() * 16;
        List<XmlTag> xmlTags = new ArrayList<>(capacity);
        for (DomFileElement<Mapper> xml : xmlFiles) {
            Mapper mapper = xml.getRootElement();
            GenericAttributeValue<String> namespace = mapper.getNamespace();
            if (null == namespace) {
                continue;
            }
            String namespaceStr = namespace.getRawText();
            if (Strings.isBlank(namespaceStr)) {
                continue;
            }

            if (namespaceStr.equals(qualifiedName)) {
                if (parent instanceof PsiClass) {
                    xmlTags.add(namespace.getXmlTag());
                } else if (parent instanceof PsiMethod) {
                    List<Statement> statements = mapper.getStatements();
                    if (null == statements || statements.isEmpty()) {
                        continue;
                    }

                    PsiMethod psiMethod = (PsiMethod) parent;
                    final String method = psiMethod.getName();

                    for (Statement statement : statements) {
                        GenericAttributeValue<String> id = statement.getId();
                        if (null == id) {
                            continue;
                        }

                        final String idStr = id.getRawText();
                        if (Strings.isBlank(idStr)) {
                            continue;
                        }

                        if (idStr.equals(method)) {
                            xmlTags.add(statement.getXmlTag());
                        }
                    }
                }
            }
        }

        // add Navigation(s)
        if (xmlTags.isEmpty()) {
            return;
        }
        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder
                .create(Resources.TO_XML)
                .setTargets(xmlTags)
                .setTooltipText("Navigate to mybatis XML file.");
        result.add(builder.createLineMarkerInfo(element));
    }

    /**
     * @return the interface(PsiClass) of the element, or null
     */
    @Nullable
    private PsiClass getInterface(@NotNull PsiElement parent) {
        // java PsiIdentifier
        if (parent instanceof PsiClass) {
            PsiClass psiClass = ((PsiClass) parent);
            if (psiClass.isInterface()) {
                return psiClass;
            }
        }
        if (parent instanceof PsiMethod) {
            PsiClass psiClass = ((PsiMethod) parent).getContainingClass();
            if (null != psiClass && psiClass.isInterface()) {
                return psiClass;
            }
        }

        return null;
    }

}
