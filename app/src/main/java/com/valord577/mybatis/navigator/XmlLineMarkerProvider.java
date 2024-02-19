package com.valord577.mybatis.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.GenericAttributeValue;
import com.valord577.mybatis.navigator.kit.Resources;
import com.valord577.mybatis.navigator.xml.MybatisXmlFileDescription;
import com.valord577.mybatis.navigator.xml.pojo.Mapper;
import com.valord577.mybatis.navigator.xml.pojo.Statement;

/**
 * <p>Navigate to JAVA Mapper from XML.</p>
 *
 * @author valor.
 */
public class XmlLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        if (!(element instanceof XmlAttribute xmlAttribute)) {
            return;
        }
        PsiFile psiFile = element.getContainingFile();
        if (null == psiFile) {
            return;
        }
        if (!(psiFile instanceof XmlFile xmlFile)) {
            return;
        }

        XmlTag xmlTag = xmlAttribute.getParent();
        if (null == xmlTag) {
            return;
        }
        if (!MybatisXmlFileDescription.Tag.isTag(xmlTag.getName(), xmlAttribute.getName())) {
            return;
        }
//        XmlAttributeValue xmlAttributeValue = xmlAttribute.getValueElement();
//        if (null == xmlAttributeValue) {
//            return;
//        }

        // find xml file
        Project project = xmlAttribute.getProject();
        DomManager domManager = DomManager.getDomManager(project);
        DomFileElement<Mapper> xmlFileElement = domManager.getFileElement(xmlFile, Mapper.class);
        if (null == xmlFileElement) {
            return;
        }

        /* filter */
        Mapper mapper = xmlFileElement.getRootElement();
        // no namespace defined
        GenericAttributeValue<String> namespace = mapper.getNamespace();
        if (null == namespace) {
            return;
        }
        // blank namespace
        String namespaceStr = namespace.getRawText();
        if (null == namespaceStr || namespaceStr.isBlank()) {
            return;
        }

        // search java mapper
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        GlobalSearchScope scope = GlobalSearchScope.projectScope(project);

        // https://github.com/valord577/mybatis-navigator/issues/1
        Module module = ModuleUtilCore.findModuleForPsiElement(element);
        if (null != module) {
            scope = GlobalSearchScope.moduleScope(module);
        }
        PsiClass psiClass = javaPsiFacade.findClass(namespaceStr, scope);
        if (null == psiClass) {
            return;
        }

        List<PsiIdentifier> targets;
        if (MybatisXmlFileDescription.Tag.MAPPER.getMajor().equals(xmlAttribute.getName())) {
            // xml mapper tag -> java mapper class name
            targets = new ArrayList<>(1);
            targets.add(psiClass.getNameIdentifier());
        } else {
            // xml other tags (select / update / insert / delete / etc...)
            DomElement domElement = domManager.getDomElement(xmlTag);
            if (!(domElement instanceof Statement)) {
                return;
            }
            PsiMethod[] psiMethods = psiClass.findMethodsByName(xmlAttribute.getValue(), true);
            int length = psiMethods.length;
            if (length < 1) {
                return;
            }
            targets = new ArrayList<>(length);
            for (PsiMethod psiMethod : psiMethods) {
                targets.add(psiMethod.getNameIdentifier());
            }
        }

        NavigationGutterIconBuilder<PsiElement> builder =
            NavigationGutterIconBuilder.create(Resources.TO_JAVA).
                setTargets(targets).
                setTooltipText("Navigate to java Mapper file.");
        /* According to the warn of Intellij, use first child instead. */
        result.add(builder.createLineMarkerInfo(xmlAttribute.getFirstChild()));
    }
}
