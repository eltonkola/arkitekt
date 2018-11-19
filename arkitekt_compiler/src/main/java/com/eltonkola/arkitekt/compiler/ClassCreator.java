package com.eltonkola.arkitekt.compiler;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class ClassCreator {

    public JavaFile create (HashMap<String, TypeElement> routesMap) throws Exception {
        final FieldSpec routes = FieldSpec.builder(new HashMap().getClass(), "routes")
                                          .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                          .initializer("new HashMap<String, Class<?>>()")
                                          .build();


        CodeBlock.Builder block = CodeBlock.builder();

        Iterator it = routesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            TypeElement type = (TypeElement)pair.getValue();

            block.addStatement("routes.put($S , $T.class)", pair.getKey(), type);
            it.remove(); // avoids a ConcurrentModificationException
        }


        TypeSpec appRoute = TypeSpec.classBuilder("AppRoute")
                                      .addModifiers(Modifier.PUBLIC)
                                      .addField(routes)
                                      .addStaticBlock(block.build())
                                      .build();


        return JavaFile.builder("com.eltonkola.config", appRoute).build();

    }

    public JavaFile createPathFile(HashMap<String, String> routesMap) {

        final TypeSpec.Builder appPaths = TypeSpec.classBuilder("AppScreens").addModifiers(Modifier.PUBLIC);


        Iterator it = routesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            String type = (String)pair.getValue();

            final FieldSpec fieldPath = FieldSpec.builder(String.class, type)
                                              .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                              .initializer("$S", pair.getKey())
                                              .build();
            appPaths.addField(fieldPath);

        }





        return JavaFile.builder("com.eltonkola.config", appPaths.build()).build();
    }
}
