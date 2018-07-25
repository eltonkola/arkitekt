package com.eltonkola.compiler;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;

import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

public class Demo {


    public static void main(String[] args) throws Exception {


        final HashMap<String, Class<?>> screens = new HashMap<String, Class<?>>();

        screens.put("/", Demo.class);
        screens.put("/create", ClassCreator.class);
        screens.put("/compile", MyCompiler.class);

        //JavaFile javaFile = new ClassCreator().create(screens);
        //javaFile.writeTo(System.out);
    }



}
