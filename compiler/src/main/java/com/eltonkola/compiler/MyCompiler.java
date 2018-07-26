package com.eltonkola.compiler;

import com.eltonkola.annotations.ScreenView;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class MyCompiler extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private ProcessingEnvironment mProcessingEnvironment;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment){
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        mProcessingEnvironment = processingEnvironment;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(ScreenView.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //get all elements annotated with AwesomeLogger
        Collection<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(ScreenView.class);

        //filter out elements we don't need
        List<TypeElement> types = new ImmutableList.Builder<TypeElement>()
                .addAll(ElementFilter.typesIn(annotatedElements))
                .build();

        final HashMap<String, TypeElement> screens = new HashMap();
        final HashMap<String, String> screenPaths = new HashMap();


        for (TypeElement type : types) {
            //interfaces are types too, but we only need classes
            //we need to check if the TypeElement is a valid class

            if (isValidClass(type)) {
                log(">>> supported type: " + type.getQualifiedName());
                Annotation annotation = type.getAnnotation(ScreenView.class);
                ScreenView info = (ScreenView)annotation;

                    try {
                        screens.put(info.path(), type);
                        screenPaths.put(info.path(), type.getSimpleName().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                // writeSourceFile(type);
            }
        }

        //create the class
        final ClassCreator classCreator = new ClassCreator();
        try {
            JavaFile javaFile = classCreator.create(screens);
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JavaFile javaFile = classCreator.createPathFile(screenPaths);
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // We are the only ones handling ScreenView annotations
        return true;
    }

    private boolean isValidClass(TypeElement type) {
        if (type.getKind() != ElementKind.CLASS) {
            messager.printMessage(Diagnostic.Kind.ERROR, type.getSimpleName() + " only classes can be annotated with Log");
            return false;
        }

        if (type.getModifiers().contains(Modifier.PRIVATE)) {
            messager.printMessage(Diagnostic.Kind.ERROR, type.getSimpleName() + " only public classes can be annotated with Log");
            return false;
        }

        return true;
    }

    private void log(String what){
        System.out.println("" + what);
    }
}
