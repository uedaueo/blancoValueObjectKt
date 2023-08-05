/*
 * blanco Framework
 * Copyright (C) 2004-2010 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.valueobjectkt;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;
import blanco.valueobjectkt.message.BlancoValueObjectKtMessage;
import blanco.valueobjectkt.resourcebundle.BlancoValueObjectKtResourceBundle;
import blanco.valueobjectkt.valueobject.BlancoValueObjectKtClassStructure;
import blanco.valueobjectkt.valueobject.BlancoValueObjectKtDelegateStructure;
import blanco.valueobjectkt.valueobject.BlancoValueObjectKtFieldStructure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that auto-generates Kotlin source code from intermediate XML files for value objects.
 *
 * This is one of the main classes of blancoValueObjectKt.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoValueObjectKtXml2KotlinClass {
    /**
     * A message.
     */
    private final BlancoValueObjectKtMessage fMsg = new BlancoValueObjectKtMessage();

    /**
     * Resource bundle object for blancoValueObject.
     */
    private final BlancoValueObjectKtResourceBundle fBundle = new BlancoValueObjectKtResourceBundle();

    /**
     * A programming language expected for the input sheet.
     */
    private int fSheetLang = BlancoCgSupportedLang.JAVA;

    public void setSheetLang(final int argSheetLang) {
        fSheetLang = argSheetLang;
    }

    /**
     * Style of the source code generation destination directory
     */
    private boolean fTargetStyleAdvanced = false;
    public void setTargetStyleAdvanced(boolean argTargetStyleAdvanced) {
        this.fTargetStyleAdvanced = argTargetStyleAdvanced;
    }
    public boolean isTargetStyleAdvanced() {
        return this.fTargetStyleAdvanced;
    }

    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return this.fVerbose;
    }

    /*
     * Settings for overriding package name.
     */
    private String fPackageSuffix = "";
    public void setPackageSuffix(String suffix) {
        this.fPackageSuffix = suffix;
    }
    public String getPackageSuffix() {
        return this.fPackageSuffix;
    }
    private String fOverridePackage = "";
    public void setOverridePackage(String overridePackage) {
        this.fOverridePackage = overridePackage;
    }
    public String getOverridePackage() {
        return this.fOverridePackage;
    }

    /**
     * A factory for blancoCg to be used internally.
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * Source file information for blancoCg to be used internally.
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for blancoCg to be used internally.
     */
    private BlancoCgClass fCgClass = null;

    /**
     * Enum information for blancoCg to be used internally.
     */
    private BlancoCgEnum fCgEnum = null;

    /**
     * Character encoding of auto-generated source files.
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    private boolean fIsXmlRootElement = false;

    public void setXmlRootElement(final boolean isXmlRootElement) {
        fIsXmlRootElement = isXmlRootElement;
    }

    /**
     * Auto-generates Kotlin source code from an intermediate XML file representing a value object.
     *
     * @param argMetaXmlSourceFile
     *            An XML file containing meta-information about the ValueObject.
     * @param argDirectoryTarget
     *            Source code generation destination directory.
     * @throws IOException
     *             If an I/O exception occurs.
     */
    public void process(final File argMetaXmlSourceFile,
            final File argDirectoryTarget) throws IOException {
        BlancoValueObjectKtXmlParser parser = new BlancoValueObjectKtXmlParser();
        parser.setVerbose(this.isVerbose());
        parser.setPackageSuffix(this.fPackageSuffix);
        parser.setOverridePackage(this.fOverridePackage);
        final BlancoValueObjectKtClassStructure[] structures = parser.parse(argMetaXmlSourceFile);
        for (int index = 0; index < structures.length; index++) {
            BlancoValueObjectKtClassStructure classStructure = structures[index];
            if (classStructure.getEnumeration()) {
                // Generates Kotlin enum code from the obtained information.
                generateEnum(classStructure, argDirectoryTarget);
            } else {
                // Generates Kotlin class code from the obtained information.
                generateClass(classStructure, argDirectoryTarget);
            }
        }
    }

    /**
     * AutoGenerate enum code from given class information value object.
     *
     * @param argEnumStructure
     * @param argDirectoryTarget
     * @throws IOException
     */
    public void generateEnum(
            final BlancoValueObjectKtClassStructure argEnumStructure,
            final File argDirectoryTarget) throws IOException {
        /*
         * The output directory will be in the format specified by the targetStyle argument of the ant task.
         * For compatibility, the output directory will be blanco/main if it is not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("generateEnum argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        // Gets an instance of the BlancoCgObjectFactory class.
        fCgFactory = BlancoCgObjectFactory.getInstance();

        // Replaces the package name if the Replace option is specified.
        // If Suffix is present, it takes precedence.
        String myPackage = argEnumStructure.getPackage();
        if (argEnumStructure.getPackageSuffix() != null && argEnumStructure.getPackageSuffix().length() > 0) {
            myPackage = myPackage + "." + argEnumStructure.getPackageSuffix();
        } else if (argEnumStructure.getOverridePackage() != null && argEnumStructure.getOverridePackage().length() > 0) {
            myPackage = argEnumStructure.getOverridePackage();
        }

        fCgSourceFile = fCgFactory.createSourceFile(myPackage, null);
        fCgSourceFile.setEncoding(fEncoding);

        fCgEnum = fCgFactory.createEnum(argEnumStructure.getName(), "");
        fCgSourceFile.getEnumList().add(fCgEnum);

        /* Enumeration is always public */
        fCgEnum.setAccess("public");

        /* Desctiption */
        fCgEnum.setDescription(argEnumStructure.getDescription());
        for (String line : argEnumStructure.getDescriptionList()) {
            fCgEnum.getLangDoc().getDescriptionList().add(line);
        }

        /* Sets the annotation for the class. */
        List<String> annotationList = argEnumStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argEnumStructure.getAnnotationList());
            /* tueda DEBUG */
//            System.out.println("/* tueda */ structure2Source : class annotation = " + argClassStructure.getAnnotationList().get(0));
        }

        /* Sets the import for the class. */
        for (int index = 0; index < argEnumStructure.getImportList()
                .size(); index++) {
            final String imported = (String) argEnumStructure.getImportList()
                    .get(index);
            fCgSourceFile.getImportList().add(imported);
        }

        for (int indexField = 0; indexField < argEnumStructure.getFieldList()
                .size(); indexField++) {
            // Processes each field.
            final BlancoValueObjectKtFieldStructure fieldStructure = (BlancoValueObjectKtFieldStructure) argEnumStructure
                    .getFieldList().get(indexField);

            // If a required field is not set, exception processing will be performed.
            if (fieldStructure.getName() == null) {
                throw new IllegalArgumentException(fMsg
                        .getMbvoji03(argEnumStructure.getName()));
            }
            /*
             * Checks if filed is a constructotr argument.
             */
            Boolean isConstArg = fieldStructure.getConstArg();

            if (isConstArg && fieldStructure.getType() == null) {
                throw new IllegalArgumentException(fMsg.getMbvoji04(
                        argEnumStructure.getName(), fieldStructure.getName()));
            }

            if (isConstArg != null && isConstArg) {
                buildConstArg(argEnumStructure, fieldStructure);
            } else {
                // Generates a field.
                buildEnumerate(argEnumStructure, fieldStructure);
            }
        }

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * build an constructor argument.
     *
     * @param argEnumStructure
     * @param argFieldStructure
     */
    private void buildConstArg(
            final BlancoValueObjectKtClassStructure argEnumStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure
    ) {
        switch (fSheetLang) {
            case BlancoCgSupportedLang.PHP:
                if (argFieldStructure.getType() == "kotlin.Int") argFieldStructure.setType("kotlin.Long");
                break;
            /* If you want to add more languages, add the case here. */
        }

        /* Determines the type; if typeKt is set, it will take precedence. */
        boolean isKtPreferred = true;
        String typeRaw = argFieldStructure.getTypeKt();
        if (typeRaw == null || typeRaw.length() == 0) {
            typeRaw = argFieldStructure.getType();
            isKtPreferred = false;
        }

        /*
         * In blancoValueObject, the property name is prefixed with "f", but in Kotlin, it is not prefixed because of the implicit getter/setter.
         */
        final BlancoCgField constParam = fCgFactory.createField(argFieldStructure.getName(),
                typeRaw, null);

        fCgEnum.getConstructorArgList().add(constParam);

        /*
         * Supports Generic. Since blancoCg assumes that <> is attached and trims the package part, it will not be set correctly if it is not set here.
         * If genericKt is set, it will take precedence.
         */
        String genericRaw = argFieldStructure.getGenericKt();
        if (!isKtPreferred && (genericRaw == null || genericRaw.length() == 0)) {
            genericRaw = argFieldStructure.getGeneric();
        }
        if (genericRaw != null && genericRaw.length() > 0) {
            constParam.getType().setGenerics(genericRaw);
        }

//        if (this.isVerbose()) {
//            System.out.println("!!! type = " + argFieldStructure.getType());
//            System.out.println("!!! generic = " + field.getType().getGenerics());
//        }

        /*
         * For the time being, private and getter/setter are not supported in blancoValueObjectKt.
         */
        constParam.setAccess("public");

        if (argFieldStructure.getOpen()) {
            constParam.setFinal(false);
        } else {
            constParam.setFinal(true);
        }

        if (argFieldStructure.getOverride()) {
            constParam.setOverride(true);
        } else {
            constParam.setOverride(false);
        }

        // Supports nullable.
        Boolean isNullable = argFieldStructure.getNullable();
        if (isNullable != null && isNullable) {
            constParam.setNotnull(false);
        } else {
            constParam.setNotnull(true);
        }

        // Supports value / variable.
        Boolean isValue = argFieldStructure.getValue();
        if (isValue != null && isValue) {
            constParam.setConst(true);
        } else {
            constParam.setConst(false);
        }

        // Sets the JavaDoc for the field.
        constParam.setDescription(argFieldStructure.getDescription());
        for (String line : argFieldStructure.getDescriptionList()) {
            constParam.getLangDoc().getDescriptionList().add(line);
        }
        constParam.getLangDoc().getDescriptionList().add(
                fBundle.getXml2javaclassFieldName(argFieldStructure.getName()));

        if (argFieldStructure.getDefault() != null || argFieldStructure.getDefaultKt() != null) {
            final String type = constParam.getType().getName();

            if (type.equals("java.util.Date")) {
                /*
                 * java.util.Date type does not allow default values.
                 */
                throw new IllegalArgumentException(fMsg.getMbvoji05(
                        argEnumStructure.getName(), argFieldStructure
                                .getName(), argFieldStructure.getDefault(),
                        type));
            }

            /*
             * In Kotlin, the default value of a property is mandatory in principle.
             * However, in the abstract class, it can be omitted if the property has the abstract modifier.
             * Nevertheless, blancoValueObjectKt will not support abstract properties for the time being.
             */

            /*
             * If there is a defaultKt, it will take precedence.
             */
            String defaultRawValue = argFieldStructure.getDefaultKt();
            if (!isKtPreferred && (defaultRawValue == null || defaultRawValue.length() == 0)) {
                defaultRawValue = argFieldStructure.getDefault();
            }

            // Sets the default value for the field.
            constParam.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.KOTLIN, fBundle.getXml2javaclassFieldDefault(defaultRawValue)));
            if (argEnumStructure.getAdjustDefaultValue() == false) {
                // If the variant of the default value is off, the value on the definition sheet is adopted as it is.
                constParam.setDefault(defaultRawValue);
            } else {

                if (type.equals("kotlin.String")) {
                    // Adds double-quotes.
                    constParam.setDefault("\""
                            + BlancoJavaSourceUtil
                            .escapeStringAsJavaSource(defaultRawValue) + "\"");
                } else if (type.equals("boolean") || type.equals("short")
                        || type.equals("int") || type.equals("long")) {
                    constParam.setDefault(defaultRawValue);
                } else if (type.equals("kotlin.Boolean")
                        || type.equals("kotlin.Int")
                        || type.equals("kotlin.Long")) {
                    constParam.setDefault("" /* Kotlin doesn't need "new". */
                            + BlancoNameUtil.trimJavaPackage(type) + "("
                            + defaultRawValue + ")");
                } else if (type.equals("java.lang.Short")) {
                    constParam.setDefault("new "
                            + BlancoNameUtil.trimJavaPackage(type)
                            + "((short) " + defaultRawValue
                            + ")");
                } else if (type.equals("java.math.BigDecimal")) {
                    fCgSourceFile.getImportList().add("java.math.BigDecimal");
                    // Converts a string to BigDecimal.
                    constParam.setDefault("new BigDecimal(\""
                            + defaultRawValue + "\")");
                } else if (type.equals("kotlin.collections.List")
                        || type.equals("kotlin.collections.ArrayList")) {
                    // In the case of ArrayList, it will adopt the given character as is.
                    // TODO: In the case of second generation blancoValueObject adoption, all class imports are appropriate.
                    fCgSourceFile.getImportList().add(type);
                    constParam.setDefault(defaultRawValue);
                } else {
                    throw new IllegalArgumentException(fMsg.getMbvoji05(
                            argEnumStructure.getName(), argFieldStructure
                                    .getName(), defaultRawValue,
                            type));
                }
            }
        }

        /* Sets the annotation for the method. */
        List<String> annotationList = argFieldStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            constParam.getAnnotationList().addAll(annotationList);
//            System.out.println("/* tueda */ method annotation = " + field.getAnnotationList().get(0));
        }

    }

    /**
     * build an enumerate.
     *
     * @param argEnumStructure
     * @param argFieldStructure
     */
    private void buildEnumerate(
            final BlancoValueObjectKtClassStructure argEnumStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure
    ) {
        final BlancoCgEnumElement enumElement = fCgFactory.createEnumElement(argFieldStructure.getName(), "");
        fCgEnum.getElementList().add(enumElement);

        // Description
        if (BlancoStringUtil.null2Blank(argFieldStructure.getDescription()).length() > 0) {
            enumElement.setDescription(argFieldStructure.getDescription());
        }

        // value
        if (BlancoStringUtil.null2Blank(argFieldStructure.getDefault()).length() > 0) {
            enumElement.setDefault(argFieldStructure.getDefault());
        }
    }

    /**
     * Auto-generates source code from a given class information value object.
     *
     * @param argClassStructure
     *            Class information.
     * @param argDirectoryTarget
     *            Output directory for Kotlin source code.
     * @throws IOException
     *             If an I/O exception occurs.
     */
    public void generateClass(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final File argDirectoryTarget) throws IOException {
        /*
         * The output directory will be in the format specified by the targetStyle argument of the ant task.
         * For compatibility, the output directory will be blanco/main if it is not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("generateClass argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        // Gets an instance of the BlancoCgObjectFactory class.
        fCgFactory = BlancoCgObjectFactory.getInstance();

        // Replaces the package name if the Replace option is specified.
        // If Suffix is present, it takes precedence.
        String myPackage = argClassStructure.getPackage();
        if (argClassStructure.getPackageSuffix() != null && argClassStructure.getPackageSuffix().length() > 0) {
            myPackage = myPackage + "." + argClassStructure.getPackageSuffix();
        } else if (argClassStructure.getOverridePackage() != null && argClassStructure.getOverridePackage().length() > 0) {
            myPackage = argClassStructure.getOverridePackage();
        }

        fCgSourceFile = fCgFactory.createSourceFile(myPackage, null);
        fCgSourceFile.setEncoding(fEncoding);

        // Creates a class.
        fCgClass = fCgFactory.createClass(argClassStructure.getName(), "");
        fCgSourceFile.getClassList().add(fCgClass);

        // Sets access to the class.
//        if (isVerbose()) {
//            System.out.println("/* tueda */ class access = " + argClassStructure.getAccess());
//        }
        String access = argClassStructure.getAccess();
        // In Kotlin, it is public by default.
        if ("public".equals(access)) {
            access = "";
        }
        // Whether it is a data class or not.
        if (argClassStructure.getData()) {
            if (access != null && access.length() > 0) {
                access += " data";
            } else {
                access = "data";
            }
        }
        fCgClass.setAccess(access);
        // Whether it is a Final class or not.
        if (argClassStructure.getData() && !argClassStructure.getFinal()) {
            if (this.isVerbose()) {
                System.out.println(fMsg
                        .getMbvoji09(argClassStructure.getName()));
            }
            fCgClass.setFinal(true);
        } else {
            fCgClass.setFinal(argClassStructure.getFinal());
        }
        // Whether it is an abstract class or not.
        // The data class cannot be an abstract class in Kotlin.
        if (argClassStructure.getData() && argClassStructure.getAbstract()) {
            System.err.println("/* tueda */ Abstract has been specified for the data class");
            throw new IllegalArgumentException(fMsg
                    .getMbvoji07(argClassStructure.getName()));
        }
        fCgClass.setAbstract(argClassStructure.getAbstract());

        // Supports generic types of classes
        if (BlancoStringUtil.null2Blank(argClassStructure.getGeneric()).length() > 0) {
            if (isVerbose()) {
                System.out.println("Class Generics = " + argClassStructure.getGeneric());
            }
            fCgClass.setGenerics(argClassStructure.getGeneric());
        }

        // Inheritance
        boolean hasExtends = false;
        if (argClassStructure.getExtends() != null && BlancoStringUtil.null2Blank(argClassStructure.getExtends().getType()).length() > 0) {
            hasExtends = true;
            BlancoCgType cgType = fCgFactory.createType(argClassStructure.getExtends().getType());
            fCgClass.getExtendClassList().add(cgType);
            if (BlancoStringUtil.null2Blank(argClassStructure.getExtends().getGenerics()).length() > 0) {
                cgType.setGenerics(argClassStructure.getExtends().getGenerics());
            }
        }
        // Implementation
        for (int index = 0; index < argClassStructure.getImplementsList()
                .size(); index++) {
            final String impl = (String) argClassStructure.getImplementsList()
                    .get(index);
            fCgClass.getImplementInterfaceList().add(
                    fCgFactory.createType(impl));
        }

        if (fIsXmlRootElement) {
            fCgClass.getAnnotationList().add("XmlRootElement");
            fCgSourceFile.getImportList().add(
                    "javax.xml.bind.annotation.XmlRootElement");
        }

        // Delegation
        for (int index = 0; index < argClassStructure.getDelegateList().size(); index++) {
            final BlancoValueObjectKtDelegateStructure delegateStructure = argClassStructure.getDelegateList().get(index);
            BlancoCgType type = fCgFactory.createType(
                    delegateStructure.getType()
            );
            if (delegateStructure.getGeneric() != null && delegateStructure.getGeneric().length() > 0) {
                type.setGenerics(delegateStructure.getGeneric());
            }
            type.setDescription(delegateStructure.getDescription()); // Adopts only the first line.

            /*
             * Delegation is achieved by a combination of implements and constructorArgs.
             *  In other words, delegation is defined by registering the constructor arguments as values in the Map with the interface name as the key.
             */
            fCgClass.getImplementInterfaceList().add(type);
            fCgClass.getDelegateMap().put(type.getName(), delegateStructure.getName());
        }

        // Sets the JavaDoc for the class.
        fCgClass.setDescription(argClassStructure.getDescription());
        for (String line : argClassStructure.getDescriptionList()) {
            fCgClass.getLangDoc().getDescriptionList().add(line);
        }

        /* Sets the annotation for the class. */
        List<String> annotationList = argClassStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argClassStructure.getAnnotationList());
            /* tueda DEBUG */
//            System.out.println("/* tueda */ structure2Source : class annotation = " + argClassStructure.getAnnotationList().get(0));
        }

        /* Sets the import for the class. */
        for (int index = 0; index < argClassStructure.getImportList()
                .size(); index++) {
            final String imported = (String) argClassStructure.getImportList()
                    .get(index);
            fCgSourceFile.getImportList().add(imported);
        }

        for (int indexField = 0; indexField < argClassStructure.getFieldList()
                .size(); indexField++) {
            // Processes each field.
            final BlancoValueObjectKtFieldStructure fieldStructure = (BlancoValueObjectKtFieldStructure) argClassStructure
                    .getFieldList().get(indexField);

            // If a required field is not set, exception processing will be performed.
            if (fieldStructure.getName() == null) {
                throw new IllegalArgumentException(fMsg
                        .getMbvoji03(argClassStructure.getName()));
            }
            if (fieldStructure.getType() == null) {
                throw new IllegalArgumentException(fMsg.getMbvoji04(
                        argClassStructure.getName(), fieldStructure.getName()));
            }

            // Generates a field.
            buildField(argClassStructure, fieldStructure);

//            // Generates a setter method.
//            buildMethodSet(argClassStructure, fieldStructure);
//
//            // Generates a getter method.
//            buildMethodGet(argClassStructure, fieldStructure);
        }

        if (argClassStructure.getGenerateToString()) {
            // Generates toString method.
            buildMethodToString(argClassStructure);
        }

        if (hasExtends) {
            /* Only one inheritence is permitted in kotlin. */
            BlancoCgType cgType = fCgClass.getExtendClassList().get(0);
            /* if override is specified to constArgs, pass them to super class */
            String overrideArgList = "";
            boolean isFirst = true;
            for (BlancoCgField param : fCgClass.getConstructorArgList()) {
                if (param.getOverride()) {
                    if (!isFirst) {
                        overrideArgList +=", ";
                    } else {
                        isFirst = false;
                    }
                    overrideArgList += param.getName();
                }
            }
            System.out.println("????? overrideArgList = " + overrideArgList);
            if (!overrideArgList.isEmpty()) {
                cgType.setConstructorArgs(overrideArgList);
            }
        }

        // TODO: Considers whether to externally flag whether to generate copyTo method.
//        BlancoBeanUtils.generateCopyToMethod(fCgSourceFile, fCgClass);

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Generates a field in the class.
     *
     * @param argClassStructure
     *            Class information.
     * @param argFieldStructure
     *            Field information.
     */
    private void buildField(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure) {

        switch (fSheetLang) {
            case BlancoCgSupportedLang.PHP:
                if (argFieldStructure.getType() == "kotlin.Int") argFieldStructure.setType("kotlin.Long");
                break;
            /* If you want to add more languages, add the case here. */
        }

        /* Determines the type; if typeKt is set, it will take precedence. */
        boolean isKtPreferred = true;
        String typeRaw = argFieldStructure.getTypeKt();
        if (typeRaw == null || typeRaw.length() == 0) {
            typeRaw = argFieldStructure.getType();
            isKtPreferred = false;
        }

        /*
         * In blancoValueObject, the property name is prefixed with "f", but in Kotlin, it is not prefixed because of the implicit getter/setter.
         */
        final BlancoCgField field = fCgFactory.createField(argFieldStructure.getName(),
                typeRaw, null);

        /*
         * Supports Generic. Since blancoCg assumes that <> is attached and trims the package part, it will not be set correctly if it is not set here.
         * If genericKt is set, it will take precedence.
         */
        String genericRaw = argFieldStructure.getGenericKt();
        if (!isKtPreferred && (genericRaw == null || genericRaw.length() == 0)) {
            genericRaw = argFieldStructure.getGeneric();
        }
        if (genericRaw != null && genericRaw.length() > 0) {
            field.getType().setGenerics(genericRaw);
        }

//        if (this.isVerbose()) {
//            System.out.println("!!! type = " + argFieldStructure.getType());
//            System.out.println("!!! generic = " + field.getType().getGenerics());
//        }

        /*
         * For the time being, private and getter/setter are not supported in blancoValueObjectKt.
         */
        field.setAccess("public");

        if (argFieldStructure.getOpen()) {
            field.setFinal(false);
        } else {
            field.setFinal(true);
        }

        if (argFieldStructure.getOverride()) {
            field.setOverride(true);
        } else {
            field.setOverride(false);
        }

        // Supports nullable.
        Boolean isNullable = argFieldStructure.getNullable();
        if (isNullable != null && isNullable) {
            field.setNotnull(false);
        } else {
            field.setNotnull(true);
        }

        // Supports value / variable.
        Boolean isValue = argFieldStructure.getValue();
        if (isValue != null && isValue) {
            field.setConst(true);
        } else {
            field.setConst(false);
        }

        /*
         * Checks if filed is a constructotr argument.
         */
        Boolean isConstArg = argFieldStructure.getConstArg();
        if (isConstArg != null && isConstArg) {
            fCgClass.getConstructorArgList().add(field);
        } else {
            fCgClass.getFieldList().add(field);
        }

        // Sets the JavaDoc for the field.
        field.setDescription(argFieldStructure.getDescription());
        for (String line : argFieldStructure.getDescriptionList()) {
            field.getLangDoc().getDescriptionList().add(line);
        }
        field.getLangDoc().getDescriptionList().add(
                fBundle.getXml2javaclassFieldName(argFieldStructure.getName()));

        if (argFieldStructure.getDefault() != null || argFieldStructure.getDefaultKt() != null) {
            final String type = field.getType().getName();

            if (type.equals("java.util.Date")) {
                /*
                 * java.util.Date type does not allow default values.
                 */
                throw new IllegalArgumentException(fMsg.getMbvoji05(
                        argClassStructure.getName(), argFieldStructure
                                .getName(), argFieldStructure.getDefault(),
                        type));
            }

            /*
             * In Kotlin, the default value of a property is mandatory in principle.
             * However, in the abstract class, it can be omitted if the property has the abstract modifier.
             * Nevertheless, blancoValueObjectKt will not support abstract properties for the time being.
             */

            /*
             * If there is a defaultKt, it will take precedence.
             */
            String defaultRawValue = argFieldStructure.getDefaultKt();
            if (!isKtPreferred && (defaultRawValue == null || defaultRawValue.length() == 0)) {
                defaultRawValue = argFieldStructure.getDefault();
            }
            if (!isConstArg && (defaultRawValue == null || defaultRawValue.length() <= 0)) {
                System.err.println("/* tueda */ The field does not have a default value. blancoValueObjectKt will not support abstract fields for the time being, so be sure to set the default value.");
                throw new IllegalArgumentException(fMsg
                        .getMbvoji08(argClassStructure.getName(), argFieldStructure.getName()));
            }

            // Sets the default value for the field.
            field.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.KOTLIN, fBundle.getXml2javaclassFieldDefault(defaultRawValue)));
            if (argClassStructure.getAdjustDefaultValue() == false) {
                // If the variant of the default value is off, the value on the definition sheet is adopted as it is.
                field.setDefault(defaultRawValue);
            } else {

                if (type.equals("kotlin.String")) {
                    // Adds double-quotes.
                    field.setDefault("\""
                            + BlancoJavaSourceUtil
                                    .escapeStringAsJavaSource(defaultRawValue) + "\"");
                } else if (type.equals("boolean") || type.equals("short")
                        || type.equals("int") || type.equals("long")) {
                    field.setDefault(defaultRawValue);
                } else if (type.equals("kotlin.Boolean")
                        || type.equals("kotlin.Int")
                        || type.equals("kotlin.Long")) {
                    field.setDefault("" /* Kotlin doesn't need "new". */
                            + BlancoNameUtil.trimJavaPackage(type) + "("
                            + defaultRawValue + ")");
                } else if (type.equals("java.lang.Short")) {
                    field.setDefault("new "
                            + BlancoNameUtil.trimJavaPackage(type)
                            + "((short) " + defaultRawValue
                            + ")");
                } else if (type.equals("java.math.BigDecimal")) {
                    fCgSourceFile.getImportList().add("java.math.BigDecimal");
                    // Converts a string to BigDecimal.
                    field.setDefault("new BigDecimal(\""
                            + defaultRawValue + "\")");
                } else if (type.equals("kotlin.collections.List")
                        || type.equals("kotlin.collections.ArrayList")) {
                    // In the case of ArrayList, it will adopt the given character as is.
                    // TODO: In the case of second generation blancoValueObject adoption, all class imports are appropriate.
                    fCgSourceFile.getImportList().add(type);
                    field.setDefault(defaultRawValue);
                } else {
                    throw new IllegalArgumentException(fMsg.getMbvoji05(
                            argClassStructure.getName(), argFieldStructure
                                    .getName(), defaultRawValue,
                            type));
                }
            }
        }

        /* Sets the annotation for the method. */
        List annotationList = argFieldStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            field.getAnnotationList().addAll(annotationList);
//            System.out.println("/* tueda */ method annotation = " + field.getAnnotationList().get(0));
        }
    }

    /**
     * Generates a set method.
     *
     * @param argFieldStructure
     *            Field information.
     */
    private void buildMethodSet(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure) {
        // Generates a setter method for each field.
        final BlancoCgMethod method = fCgFactory.createMethod("set"
                + getFieldNameAdjustered(argClassStructure, argFieldStructure),
                fBundle.getXml2javaclassSetJavadoc01(argFieldStructure
                        .getName()));
        fCgClass.getMethodList().add(method);

        // JavaDoc configuration of the method.
        if (argFieldStructure.getDescription() != null) {
            method.getLangDoc().getDescriptionList().add(
                    fBundle.getXml2javaclassSetJavadoc02(argFieldStructure
                            .getDescription()));
        }
        for (String line : argFieldStructure.getDescriptionList()) {
            method.getLangDoc().getDescriptionList().add(line);
        }

        method.getParameterList().add(
                fCgFactory.createParameter("arg"
                        + getFieldNameAdjustered(argClassStructure,
                                argFieldStructure),
                        argFieldStructure.getType(),
                        fBundle.getXml2javaclassSetArgJavadoc(argFieldStructure
                                .getName())));

        // Method implementation.
        method.getLineList().add(
                "f"
                        + getFieldNameAdjustered(argClassStructure,
                                argFieldStructure)
                        + " = "
                        + "arg"
                        + getFieldNameAdjustered(argClassStructure,
                                argFieldStructure) + ";");
    }

    /**
     * Generates a get method.
     *
     * @param argFieldStructure
     *            Field information.
     */
    private void buildMethodGet(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure) {
        // Generates a getter method for each field.
        final BlancoCgMethod method = fCgFactory.createMethod("get"
                + getFieldNameAdjustered(argClassStructure, argFieldStructure),
                fBundle.getXml2javaclassGetJavadoc01(argFieldStructure
                        .getName()));
        fCgClass.getMethodList().add(method);

        // JavaDoc configuration of the method.
        if (argFieldStructure.getDescription() != null) {
            method.getLangDoc().getDescriptionList().add(
                    fBundle.getXml2javaclassGetJavadoc02(argFieldStructure
                            .getDescription()));
        }
        for (String line : argFieldStructure.getDescriptionList()) {
            method.getLangDoc().getDescriptionList().add(line);
        }
        if (argFieldStructure.getDefault() != null) {
            method.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.JAVA, fBundle.getXml2javaclassGetDefaultJavadoc(argFieldStructure
                            .getDefault())));
        }

        method.setReturn(fCgFactory.createReturn(argFieldStructure.getType(),
                fBundle.getXml2javaclassGetReturnJavadoc(argFieldStructure
                        .getName())));

        // Method implementation.
        method.getLineList().add(
                "return f"
                        + getFieldNameAdjustered(argClassStructure,
                                argFieldStructure) + ";");
    }

    /**
     * Generates toString method.
     *
     * @param argClassStructure
     *            Class information.
     */
    private void buildMethodToString(
            final BlancoValueObjectKtClassStructure argClassStructure) {
        final BlancoCgMethod method = fCgFactory.createMethod("toString",
                "Gets the string representation of this value object.");
        fCgClass.getMethodList().add(method);

        method.getLangDoc().getDescriptionList().add("<P>Precautions for use</P>");
        method.getLangDoc().getDescriptionList().add("<UL>");
        method.getLangDoc().getDescriptionList().add(
                "<LI>Only the shallow range of the object will be subject to the stringification process.");
        method.getLangDoc().getDescriptionList().add(
                "<LI>Do not use this method if the object has a circular reference.");
        method.getLangDoc().getDescriptionList().add("</UL>");
        method.setReturn(fCgFactory.createReturn("java.lang.String",
                "String representation of a value object."));

        /*
         * For the time being, toString override is not allowed in blancoValueObjectKt.
         * 2020/04/20 hmatsumoto
         * Sets override.
         */
        method.setOverride(true);
        method.setFinal(true);

        final List<java.lang.String> listLine = method.getLineList();

        listLine.add("val buf = StringBuffer()");
        listLine.add("buf.append(\"" + argClassStructure.getPackage() + "."
                + argClassStructure.getName() + "[\")");
        for (int indexField = 0; indexField < argClassStructure.getFieldList()
                .size(); indexField++) {
            final BlancoValueObjectKtFieldStructure field = (BlancoValueObjectKtFieldStructure) argClassStructure
                    .getFieldList().get(indexField);

            /*
             * 2020/04/20 hmatsumoto
             * Fixed the field name without "f".
             */
            if (field.getType().endsWith("[]") == false) {
                listLine.add("buf.append(\"" + (indexField == 0 ? "" : ",")
                        + field.getName() + "=\" + " + field.getName()
                        + ")");
            } else {
                // 2006.05.31 t.iga In the case of arrays, it is necessary to first check whether the array itself is null or not.
                listLine.add("if (" + field.getName() + " == null) {");
                // If it is the 0th item, it will be given special treatment without a comma.
                listLine.add("buf.append(" + (indexField == 0 ? "\"" :
                // If it is not the 0th, a comma is always given.
                        "\",") + field.getName() + "=null\")");
                listLine.add("} else {");

                // In the case of arrays, uses deep toString.
                listLine.add("buf.append("
                // If it is the 0th item, it will be given special treatment without a comma.
                        + (indexField == 0 ? "\"" :
                        // If it is not the 0th, a comma is always given.
                                "\",") + field.getName() + "=[\")");
                listLine.add("for (int index = 0; index < "
                        + field.getName() + ".length; index++) {");
                // 2006.05.31 t.iga
                // To make it similar to toString in ArrayList, etc., adds a half-width space after the comma.
                listLine.add("buf.append((index == 0 ? \"\" : \", \") + "
                        + field.getName() + "[index])");
                listLine.add("}");
                listLine.add("buf.append(\"]\")");
                listLine.add("}");
            }
        }
        listLine.add("buf.append(\"]\")");
        listLine.add("return buf.toString()");
    }

    /**
     * Gets the adjusted field name.
     *
     * @param argFieldStructure
     *            Field information.
     * @return Adjusted field name.
     */
    private String getFieldNameAdjustered(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure) {
        return (argClassStructure.getAdjustFieldName() == false ? argFieldStructure
                .getName()
                : BlancoNameAdjuster.toClassName(argFieldStructure.getName()));
    }
}
