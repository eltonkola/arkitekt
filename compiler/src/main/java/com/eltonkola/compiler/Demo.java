package com.eltonkola.compiler;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class Demo {


    public static void main(String[] args) throws Exception {


        //final HashMap<String, TypeElement> screens = new HashMap();

        //screens.put("/", new FakeElement("/") {}Demo.class);
        //screens.put("/create", ClassCreator.class);
        //screens.put("/compile", MyCompiler.class);
        //
        //JavaFile javaFile = new ClassCreator().createPathFile(screens);
        //javaFile.writeTo(System.out);
    }
}
