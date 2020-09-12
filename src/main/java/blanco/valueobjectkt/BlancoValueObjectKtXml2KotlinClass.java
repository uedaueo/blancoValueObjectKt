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
import java.util.List;

/**
 * バリューオブジェクト用中間XMLファイルから Kotlinソースコードを自動生成するクラス。
 *
 * blancoValueObjectKtの主たるクラスのひとつです。
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoValueObjectKtXml2KotlinClass {
    /**
     * メッセージ。
     */
    private final BlancoValueObjectKtMessage fMsg = new BlancoValueObjectKtMessage();

    /**
     * blancoValueObjectのリソースバンドルオブジェクト。
     */
    private final BlancoValueObjectKtResourceBundle fBundle = new BlancoValueObjectKtResourceBundle();

    /**
     * 入力シートに期待するプログラミング言語
     */
    private int fSheetLang = BlancoCgSupportedLang.JAVA;

    public void setSheetLang(final int argSheetLang) {
        fSheetLang = argSheetLang;
    }

    /**
     * ソースコード生成先ディレクトリのスタイル
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

    /**
     * 内部的に利用するblancoCg用ファクトリ。
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * 内部的に利用するblancoCg用ソースファイル情報。
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * 内部的に利用するblancoCg用クラス情報。
     */
    private BlancoCgClass fCgClass = null;

    /**
     * 自動生成するソースファイルの文字エンコーディング。
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
     * バリューオブジェクトを表現する中間XMLファイルから、Javaソースコードを自動生成します。
     *
     * @param argMetaXmlSourceFile
     *            ValueObjectに関するメタ情報が含まれているXMLファイル
     * @param argDirectoryTarget
     *            ソースコード生成先ディレクトリ
     * @throws IOException
     *             入出力例外が発生した場合
     */
    public void process(final File argMetaXmlSourceFile,
            final File argDirectoryTarget) throws IOException {
        BlancoValueObjectKtXmlParser parser = new BlancoValueObjectKtXmlParser();
        parser.setVerbose(this.isVerbose());
        final BlancoValueObjectKtClassStructure[] structures = parser.parse(argMetaXmlSourceFile);
        for (int index = 0; index < structures.length; index++) {
            // 得られた情報からKotlinソースコードを生成します。
            structure2Source(structures[index], argDirectoryTarget);
        }
    }

    /**
     * 与えられたクラス情報バリューオブジェクトから、ソースコードを自動生成します。
     *
     * @param argClassStructure
     *            クラス情報
     * @param argDirectoryTarget
     *            Kotlinソースコードの出力先ディレクトリ
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public void structure2Source(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final File argDirectoryTarget) throws IOException {
        /*
         * 出力ディレクトリはant taskのtargetStyel引数で
         * 指定された書式で出力されます。
         * 従来と互換性を保つために、指定がない場合は blanco/main
         * となります。
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
            System.out.println("structure2Source argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        // BlancoCgObjectFactoryクラスのインスタンスを取得します。
        fCgFactory = BlancoCgObjectFactory.getInstance();

        fCgSourceFile = fCgFactory.createSourceFile(argClassStructure
                .getPackage(), null);
        fCgSourceFile.setEncoding(fEncoding);

        // クラスを作成します。
        fCgClass = fCgFactory.createClass(argClassStructure.getName(), "");
        fCgSourceFile.getClassList().add(fCgClass);

        // クラスのアクセスを設定。
//        if (isVerbose()) {
//            System.out.println("/* tueda */ class access = " + argClassStructure.getAccess());
//        }
        String access = argClassStructure.getAccess();
        // data クラスかどうか
        if (argClassStructure.getData()) {
            if (access != null && access.length() > 0) {
                access += " data";
            } else {
                access = "data";
            }
        }
        fCgClass.setAccess(access);
        // Finalクラスかどうか。
        fCgClass.setFinal(argClassStructure.getFinal());
        // 抽象クラスかどうか。
        // kotlin では dataクラスは抽象クラスになれない
        if (argClassStructure.getData() && argClassStructure.getAbstract()) {
            System.err.println("/* tueda */ dataクラスに対してabstractが指定されています");
            throw new IllegalArgumentException(fMsg
                    .getMbvoji07(argClassStructure.getName()));
        }
        fCgClass.setAbstract(argClassStructure.getAbstract());

        // クラスの総称型に対応
        if (BlancoStringUtil.null2Blank(argClassStructure.getGeneric()).length() > 0) {
            if (isVerbose()) {
                System.out.println("Class Generics = " + argClassStructure.getGeneric());
            }
            fCgClass.setGenerics(argClassStructure.getGeneric());
        }

        // 継承
        if (BlancoStringUtil.null2Blank(argClassStructure.getExtends())
                .length() > 0) {
            fCgClass.getExtendClassList().add(
                    fCgFactory.createType(argClassStructure.getExtends()));
        }
        // 実装
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

        // 委譲
        for (int index = 0; index < argClassStructure.getDelegateList().size(); index++) {
            final BlancoValueObjectKtDelegateStructure delegateStructure = argClassStructure.getDelegateList().get(index);
            BlancoCgType type = fCgFactory.createType(
                    delegateStructure.getType()
            );
            if (delegateStructure.getGeneric() != null && delegateStructure.getGeneric().length() > 0) {
                type.setGenerics(delegateStructure.getGeneric());
            }
            type.setDescription(delegateStructure.getDescription()); // 1行目のみ採用

            /*
             * 委譲は、implements と constructorArgs の組合せで実現されます。
             * すなわち、インタフェイス名をキーにコンストラクタ引数を値として
             * Mapに登録しておくことで、委譲を定義します。
             */
            fCgClass.getImplementInterfaceList().add(type);
            fCgClass.getDelegateMap().put(type.getName(), delegateStructure.getName());
        }

        // クラスのJavaDocを設定します。
        fCgClass.setDescription(argClassStructure.getDescription());
        for (String line : argClassStructure.getDescriptionList()) {
            fCgClass.getLangDoc().getDescriptionList().add(line);
        }

        /* クラスのannotation を設定します */
        List annotationList = argClassStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argClassStructure.getAnnotationList());
            /* tueda DEBUG */
//            System.out.println("/* tueda */ structure2Source : class annotation = " + argClassStructure.getAnnotationList().get(0));
        }

        /* クラスの import を設定します */
        for (int index = 0; index < argClassStructure.getImportList()
                .size(); index++) {
            final String imported = (String) argClassStructure.getImportList()
                    .get(index);
            fCgSourceFile.getImportList().add(imported);
        }

        for (int indexField = 0; indexField < argClassStructure.getFieldList()
                .size(); indexField++) {
            // おのおののフィールドを処理します。
            final BlancoValueObjectKtFieldStructure fieldStructure = (BlancoValueObjectKtFieldStructure) argClassStructure
                    .getFieldList().get(indexField);

            // 必須項目が未設定の場合には例外処理を実施します。
            if (fieldStructure.getName() == null) {
                throw new IllegalArgumentException(fMsg
                        .getMbvoji03(argClassStructure.getName()));
            }
            if (fieldStructure.getType() == null) {
                throw new IllegalArgumentException(fMsg.getMbvoji04(
                        argClassStructure.getName(), fieldStructure.getName()));
            }

            // フィールドの生成。
            buildField(argClassStructure, fieldStructure);

//            // セッターメソッドの生成。
//            buildMethodSet(argClassStructure, fieldStructure);
//
//            // ゲッターメソッドの生成。
//            buildMethodGet(argClassStructure, fieldStructure);
        }

        if (argClassStructure.getGenerateToString()) {
            // toStringメソッドの生成。
            buildMethodToString(argClassStructure);
        }

        // TODO copyTo メソッドの生成有無を外部フラグ化するかどうか検討すること。
//        BlancoBeanUtils.generateCopyToMethod(fCgSourceFile, fCgClass);

        // 収集された情報を元に実際のソースコードを自動生成。
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * クラスにフィールドを生成します。
     *
     * @param argClassStructure
     *            クラス情報。
     * @param argFieldStructure
     *            フィールド情報。
     */
    private void buildField(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure) {

        switch (fSheetLang) {
            case BlancoCgSupportedLang.PHP:
                if (argFieldStructure.getType() == "kotlin.Int") argFieldStructure.setType("kotlin.Long");
                break;
            /* 対応言語を増やす場合はここに case を追記します */
        }

        /* 型を決定します。typeKtが設定されている場合はそちらを優先します */
        boolean isKtPreferred = true;
        String typeRaw = argFieldStructure.getTypeKt();
        if (typeRaw == null || typeRaw.length() == 0) {
            typeRaw = argFieldStructure.getType();
            isKtPreferred = false;
        }

        /*
         * blancoValueObjectではプロパティ名の前にfをつける流儀であるが、
         * kotlinについては暗黙のgetter/setterを使う都合上、つけない。
         */
        final BlancoCgField field = fCgFactory.createField(argFieldStructure.getName(),
                typeRaw, null);

        /*
         * Generic に対応します。blancoCg 側では <> が付いている前提かつ
         * package部をtrimするので、ここで設定しないと正しく設定されません。
         * genericKt が設定されている場合はそちらを優先します。
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
         * 当面の間、blancoValueObjectKt ではprivateやgetter/setter,
         * open には対応しません。
         */
        field.setAccess("public");
        field.setFinal(true);

        // nullable に対応します。
        Boolean isNullable = argFieldStructure.getNullable();
        if (isNullable != null && isNullable) {
            field.setNotnull(false);
        } else {
            field.setNotnull(true);
        }

        // value / variable に対応します。
        Boolean isValue = argFieldStructure.getValue();
        if (isValue != null && isValue) {
            field.setConst(true);
        } else {
            field.setConst(false);
        }

        /*
         * filed がconstructotr引数かどうをチェック
         */
        Boolean isConstArg = argFieldStructure.getConstArg();
        if (isConstArg != null && isConstArg) {
            fCgClass.getConstructorArgList().add(field);
        } else {
            fCgClass.getFieldList().add(field);
        }

        // フィールドのJavaDocを設定します。
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
                 * java.util.Date 型ではデフォルト値を許容しません。
                 */
                throw new IllegalArgumentException(fMsg.getMbvoji05(
                        argClassStructure.getName(), argFieldStructure
                                .getName(), argFieldStructure.getDefault(),
                        type));
            }

            /*
             * kotlinでは プロパティのデフォルト値は原則必須
             * ただし、abstract クラスにおいては、プロパティに abstract 修飾子が
             * ついている場合は省略可。
             * とはいえ、blancoValueObjectKt では当面abstractプロパティはサポートしません。
             */

            /*
             * defaultKt があればそちらを優先します。
             */
            String defaultRawValue = argFieldStructure.getDefaultKt();
            if (!isKtPreferred && (defaultRawValue == null || defaultRawValue.length() == 0)) {
                defaultRawValue = argFieldStructure.getDefault();
            }
            if (!isConstArg && (defaultRawValue == null || defaultRawValue.length() <= 0)) {
                System.err.println("/* tueda */ フィールドにデフォルト値が設定されていません。blancoValueObjectKtは当面の間、abstractフィールドはサポートしませんので、必ずデフォルト値を設定してください。");
                throw new IllegalArgumentException(fMsg
                        .getMbvoji08(argClassStructure.getName(), argFieldStructure.getName()));
            }

            // フィールドのデフォルト値を設定します。
            field.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.KOTLIN, fBundle.getXml2javaclassFieldDefault(defaultRawValue)));
            if (argClassStructure.getAdjustDefaultValue() == false) {
                // デフォルト値の変形がoffの場合には、定義書上の値をそのまま採用。
                field.setDefault(defaultRawValue);
            } else {

                if (type.equals("kotlin.String")) {
                    // ダブルクオートを付与します。
                    field.setDefault("\""
                            + BlancoJavaSourceUtil
                                    .escapeStringAsJavaSource(defaultRawValue) + "\"");
                } else if (type.equals("boolean") || type.equals("short")
                        || type.equals("int") || type.equals("long")) {
                    field.setDefault(defaultRawValue);
                } else if (type.equals("kotlin.Boolean")
                        || type.equals("kotlin.Int")
                        || type.equals("kotlin.Long")) {
                    field.setDefault("" /* kotlin には new は不要です */
                            + BlancoNameUtil.trimJavaPackage(type) + "("
                            + defaultRawValue + ")");
                } else if (type.equals("java.lang.Short")) {
                    field.setDefault("new "
                            + BlancoNameUtil.trimJavaPackage(type)
                            + "((short) " + defaultRawValue
                            + ")");
                } else if (type.equals("java.math.BigDecimal")) {
                    fCgSourceFile.getImportList().add("java.math.BigDecimal");
                    // 文字列からBigDecimalへと変換します。
                    field.setDefault("new BigDecimal(\""
                            + defaultRawValue + "\")");
                } else if (type.equals("kotlin.collections.List")
                        || type.equals("kotlin.collections.ArrayList")) {
                    // ArrayListの場合には、与えられた文字をそのまま採用します。
                    // TODO 第2世代blancoValueObject採用場合には、全クラスインポートが妥当。
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

        /* メソッドの annotation を設定します */
        List annotationList = argFieldStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            field.getAnnotationList().addAll(annotationList);
//            System.out.println("/* tueda */ method annotation = " + field.getAnnotationList().get(0));
        }
    }

    /**
     * setメソッドを生成します。
     *
     * @param argFieldStructure
     *            フィールド情報。
     */
    private void buildMethodSet(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure) {
        // おのおののフィールドに対するセッターメソッドを生成します。
        final BlancoCgMethod method = fCgFactory.createMethod("set"
                + getFieldNameAdjustered(argClassStructure, argFieldStructure),
                fBundle.getXml2javaclassSetJavadoc01(argFieldStructure
                        .getName()));
        fCgClass.getMethodList().add(method);

        // メソッドの JavaDoc設定。
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

        // メソッドの実装
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
     * getメソッドを生成します。
     *
     * @param argFieldStructure
     *            フィールド情報。
     */
    private void buildMethodGet(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure) {
        // おのおののフィールドに対するゲッターメソッドを生成します。
        final BlancoCgMethod method = fCgFactory.createMethod("get"
                + getFieldNameAdjustered(argClassStructure, argFieldStructure),
                fBundle.getXml2javaclassGetJavadoc01(argFieldStructure
                        .getName()));
        fCgClass.getMethodList().add(method);

        // メソッドの JavaDoc設定。
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

        // メソッドの実装
        method.getLineList().add(
                "return f"
                        + getFieldNameAdjustered(argClassStructure,
                                argFieldStructure) + ";");
    }

    /**
     * toStringメソッドを生成します。
     *
     * @param argClassStructure
     *            クラス情報。
     */
    private void buildMethodToString(
            final BlancoValueObjectKtClassStructure argClassStructure) {
        final BlancoCgMethod method = fCgFactory.createMethod("toString",
                "このバリューオブジェクトの文字列表現を取得します。");
        fCgClass.getMethodList().add(method);

        method.getLangDoc().getDescriptionList().add("<P>使用上の注意</P>");
        method.getLangDoc().getDescriptionList().add("<UL>");
        method.getLangDoc().getDescriptionList().add(
                "<LI>オブジェクトのシャロー範囲のみ文字列化の処理対象となります。");
        method.getLangDoc().getDescriptionList().add(
                "<LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。");
        method.getLangDoc().getDescriptionList().add("</UL>");
        method.setReturn(fCgFactory.createReturn("java.lang.String",
                "バリューオブジェクトの文字列表現。"));

        /*
         * 当面の間、blancoValueObjectKt ではtoStringのoverrideを許しません。
         * 2020/04/20 hmatsumoto
         * overrideを設定しました。
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
             * フィールド名にfをつけないように修正
             */
            if (field.getType().endsWith("[]") == false) {
                listLine.add("buf.append(\"" + (indexField == 0 ? "" : ",")
                        + field.getName() + "=\" + " + field.getName()
                        + ")");
            } else {
                // 2006.05.31 t.iga 配列の場合には、先に
                // その配列自身がnullかどうかのチェックが必要です。
                listLine.add("if (" + field.getName() + " == null) {");
                // 0番目の項目である場合にはカンマなしの特別扱いをします。
                listLine.add("buf.append(" + (indexField == 0 ? "\"" :
                // 0番目ではない場合には、常にカンマを付与します。
                        "\",") + field.getName() + "=null\")");
                listLine.add("} else {");

                // 配列の場合にはディープにtoStringします。
                listLine.add("buf.append("
                // 0番目の項目である場合にはカンマなしの特別扱いをします。
                        + (indexField == 0 ? "\"" :
                        // 0番目ではない場合には、常にカンマを付与します。
                                "\",") + field.getName() + "=[\")");
                listLine.add("for (int index = 0; index < "
                        + field.getName() + ".length; index++) {");
                // 2006.05.31 t.iga
                // ArrayListなどのtoStringと同様になるように、カンマのあとに半角スペースを付与するようにします。
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
     * 調整済みのフィールド名を取得します。
     *
     * @param argFieldStructure
     *            フィールド情報。
     * @return 調整後のフィールド名。
     */
    private String getFieldNameAdjustered(
            final BlancoValueObjectKtClassStructure argClassStructure,
            final BlancoValueObjectKtFieldStructure argFieldStructure) {
        return (argClassStructure.getAdjustFieldName() == false ? argFieldStructure
                .getName()
                : BlancoNameAdjuster.toClassName(argFieldStructure.getName()));
    }
}
