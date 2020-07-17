/*
 * blanco Framework
 * Copyright (C) 2004-2008 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.valueobjectkt;

import blanco.cg.BlancoCgSupportedLang;
import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;
import blanco.valueobjectkt.message.BlancoValueObjectKtMessage;
import blanco.valueobjectkt.resourcebundle.BlancoValueObjectKtResourceBundle;
import blanco.valueobjectkt.valueobject.BlancoValueObjectKtClassStructure;
import blanco.valueobjectkt.valueobject.BlancoValueObjectKtFieldStructure;
import blanco.xml.bind.BlancoXmlBindingUtil;
import blanco.xml.bind.BlancoXmlUnmarshaller;
import blanco.xml.bind.valueobject.BlancoXmlAttribute;
import blanco.xml.bind.valueobject.BlancoXmlDocument;
import blanco.xml.bind.valueobject.BlancoXmlElement;

import java.io.File;
import java.util.*;

/**
 * blancoValueObjectの 中間XMLファイル形式をパース(読み書き)するクラス。
 *
 * @author IGA Tosiki
 */
public class BlancoValueObjectKtXmlParser {
    /**
     * メッセージ。
     */
    private final BlancoValueObjectKtMessage fMsg = new BlancoValueObjectKtMessage();

    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return fVerbose;
    }

    /**
     * blancoValueObjectのリソースバンドルオブジェクト。
     */
    private final static BlancoValueObjectKtResourceBundle fBundle = new BlancoValueObjectKtResourceBundle();

    public static Map<String, Integer> mapCommons = new HashMap<String, Integer>() {
        {put(fBundle.getMeta2xmlElementCommon(), BlancoCgSupportedLang.JAVA);}
        {put(fBundle.getMeta2xmlElementCommonCs(), BlancoCgSupportedLang.CS);}
        {put(fBundle.getMeta2xmlElementCommonJs(), BlancoCgSupportedLang.JS);}
        {put(fBundle.getMeta2xmlElementCommonVb(), BlancoCgSupportedLang.VB);}
        {put(fBundle.getMeta2xmlElementCommonPhp(), BlancoCgSupportedLang.PHP);}
        {put(fBundle.getMeta2xmlElementCommonRuby(), BlancoCgSupportedLang.RUBY);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.PYTHON);}
    };

    public static Map<String, String> classList = null;

    /**
     * 中間XMLファイルのXMLドキュメントをパースして、バリューオブジェクト情報の配列を取得します。
     *
     * @param argMetaXmlSourceFile
     *            中間XMLファイル。
     * @return パースの結果得られたバリューオブジェクト情報の配列。
     */
    public BlancoValueObjectKtClassStructure[] parse(
            final File argMetaXmlSourceFile) {
        final BlancoXmlDocument documentMeta = new BlancoXmlUnmarshaller()
                .unmarshal(argMetaXmlSourceFile);
        if (documentMeta == null) {
            return null;
        }

        return parse(documentMeta);

    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、バリューオブジェクト情報の配列を取得します。
     *
     * @param argXmlDocument
     *            中間XMLファイルのXMLドキュメント。
     * @return パースの結果得られたバリューオブジェクト情報の配列。
     */
    public BlancoValueObjectKtClassStructure[] parse(
            final BlancoXmlDocument argXmlDocument) {
        final List<BlancoValueObjectKtClassStructure> listStructure = new ArrayList<BlancoValueObjectKtClassStructure>();

        // ルートエレメントを取得します。
        final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                .getDocumentElement(argXmlDocument);
        if (elementRoot == null) {
            // ルートエレメントが無い場合には処理中断します。
            return null;
        }

        // sheet(Excelシート)のリストを取得します。
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(elementRoot, "sheet");

        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            final BlancoXmlElement elementSheet = listSheet.get(index);

            /*
             * Java以外の言語用に記述されたシートにも対応．
             */
            List<BlancoXmlElement> listCommon = null;
            int sheetLang = BlancoCgSupportedLang.JAVA;
            for (String common : mapCommons.keySet()) {
                listCommon = BlancoXmlBindingUtil
                        .getElementsByTagName(elementSheet,
                                common);
                if (listCommon.size() != 0) {
                    BlancoXmlAttribute attr = new BlancoXmlAttribute();
                    attr.setType("CDATA");
                    attr.setQName("style");
                    attr.setLocalName("style");

                    sheetLang = mapCommons.get(common);
                    attr.setValue(new BlancoCgSupportedLang().convertToString(sheetLang));

                    elementSheet.getAtts().add(attr);

                    /* tueda DEBUG */
                    if (this.isVerbose()) {
                        System.out.println("/* tueda */ style = " + BlancoXmlBindingUtil.getAttribute(elementSheet, "style"));
                    }

                    break;
                }
            }

            if (listCommon == null || listCommon.size() == 0) {
                // commonが無い場合にはスキップします。
                continue;
            }

            // 最初のアイテムのみ処理しています。
            final BlancoXmlElement elementCommon = listCommon.get(0);
            final String name = BlancoXmlBindingUtil.getTextContent(
                    elementCommon, "name");
            if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
                continue;
            }

            BlancoValueObjectKtClassStructure objClassStructure = null;
            switch (sheetLang) {
                case BlancoCgSupportedLang.JAVA:
                    objClassStructure = parseElementSheet(elementSheet);
                    break;
                case BlancoCgSupportedLang.PHP:
                    objClassStructure = parseElementSheetPhp(elementSheet, classList);
                    /* NOT YET SUPPORT ANOTHER LANGUAGES */
            }

            if (objClassStructure != null) {
                // 得られた情報を記憶します。
                listStructure.add(objClassStructure);
            }
        }

        final BlancoValueObjectKtClassStructure[] result = new BlancoValueObjectKtClassStructure[listStructure
                .size()];
        listStructure.toArray(result);
        return result;
    }

    /**
     * 中間XMLファイル形式の「sheet」XMLエレメントをパースして、バリューオブジェクト情報を取得します。
     *
     * @param argElementSheet
     *            中間XMLファイルの「sheet」XMLエレメント。
     * @return パースの結果得られたバリューオブジェクト情報。「name」が見つからなかった場合には nullを戻します。
     */
    public BlancoValueObjectKtClassStructure parseElementSheet(
            final BlancoXmlElement argElementSheet) {
        final BlancoValueObjectKtClassStructure objClassStructure = new BlancoValueObjectKtClassStructure();
        final List<BlancoXmlElement> listCommon = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        "blancovalueobject-common");
        if (listCommon == null || listCommon.size() == 0) {
            // commonが無い場合にはスキップします。
            return null;
        }
        final BlancoXmlElement elementCommon = listCommon.get(0);
        objClassStructure.setName(BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name"));
        objClassStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                elementCommon, "package"));

        objClassStructure.setDescription(BlancoXmlBindingUtil.getTextContent(
                elementCommon, "description"));
        if (BlancoStringUtil.null2Blank(objClassStructure.getDescription())
                .length() > 0) {
            final String[] lines = BlancoNameUtil.splitString(objClassStructure
                    .getDescription(), '\n');
            for (int index = 0; index < lines.length; index++) {
                if (index == 0) {
                    objClassStructure.setDescription(lines[index]);
                } else {
                    // 複数行の description については、これを分割して格納します。
                    // ２行目からは、適切に文字参照エンコーディングが実施されているものと仮定します。
                    objClassStructure.getDescriptionList().add(lines[index]);
                }
            }
        }

        objClassStructure.setAccess(BlancoXmlBindingUtil.getTextContent(
                elementCommon, "access"));
        objClassStructure.setAbstract("true".equals(BlancoXmlBindingUtil
                .getTextContent(elementCommon, "abstract")));
        objClassStructure.setData("true".equals(BlancoXmlBindingUtil
                .getTextContent(elementCommon, "data")));
        objClassStructure.setGenerateToString("true"
                .equals(BlancoXmlBindingUtil.getTextContent(elementCommon,
                        "generateToString")));
        objClassStructure.setAdjustFieldName("true".equals(BlancoXmlBindingUtil
                .getTextContent(elementCommon, "adjustFieldName")));
        objClassStructure.setAdjustDefaultValue("true"
                .equals(BlancoXmlBindingUtil.getTextContent(elementCommon,
                        "adjustDefaultValue")));
        objClassStructure
                .setFieldList(new ArrayList<blanco.valueobjectkt.valueobject.BlancoValueObjectKtFieldStructure>());

        if (BlancoStringUtil.null2Blank(objClassStructure.getName()).trim()
                .length() == 0) {
            // 名前が無いものはスキップします。
            return null;
        }

        if (objClassStructure.getPackage() == null) {
            throw new IllegalArgumentException(fMsg
                    .getMbvoji01(objClassStructure.getName()));
        }

        final List<BlancoXmlElement> extendsList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        "blancovalueobject-extends");
        if (extendsList != null && extendsList.size() != 0) {
            final BlancoXmlElement elementExtendsRoot = extendsList.get(0);
            objClassStructure.setExtends(BlancoXmlBindingUtil.getTextContent(
                    elementExtendsRoot, "name"));
        }

        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        "blancovalueobject-implements");
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            final List<BlancoXmlElement> listInterfaceChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementInterfaceRoot, "interface");
            for (int index = 0; index < listInterfaceChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listInterfaceChildNodes
                        .get(index);

                final String interfaceName = BlancoXmlBindingUtil
                        .getTextContent(elementList, "name");
                if (interfaceName == null || interfaceName.trim().length() == 0) {
                    continue;
                }
                objClassStructure.getImplementsList().add(
                        BlancoXmlBindingUtil
                                .getTextContent(elementList, "name"));
            }
        }

        final List<BlancoXmlElement> listList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, "blancovalueobject-list");
        if (listList != null && listList.size() != 0) {
            final BlancoXmlElement elementListRoot = listList.get(0);
            final List<BlancoXmlElement> listChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementListRoot, "field");
            for (int index = 0; index < listChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listChildNodes.get(index);
                final BlancoValueObjectKtFieldStructure fieldStructure = new BlancoValueObjectKtFieldStructure();

                fieldStructure.setNo(BlancoXmlBindingUtil.getTextContent(
                        elementList, "no"));
                fieldStructure.setName(BlancoXmlBindingUtil.getTextContent(
                        elementList, "name"));
                if (fieldStructure.getName() == null
                        || fieldStructure.getName().trim().length() == 0) {
                    continue;
                }

                fieldStructure.setType(BlancoXmlBindingUtil.getTextContent(
                        elementList, "type"));

                fieldStructure.setDescription(BlancoXmlBindingUtil
                        .getTextContent(elementList, "description"));
                final String[] lines = BlancoNameUtil.splitString(
                        fieldStructure.getDescription(), '\n');
                for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                    if (indexLine == 0) {
                        fieldStructure.setDescription(lines[indexLine]);
                    } else {
                        // 複数行の description については、これを分割して格納します。
                        // ２行目からは、適切に文字参照エンコーディングが実施されているものと仮定します。
                        fieldStructure.getDescriptionList().add(
                                lines[indexLine]);
                    }
                }

                fieldStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                        elementList, "default"));
                fieldStructure.setMinLength(BlancoXmlBindingUtil
                        .getTextContent(elementList, "minLength"));
                fieldStructure.setMaxLength(BlancoXmlBindingUtil
                        .getTextContent(elementList, "maxLength"));
                fieldStructure.setLength(BlancoXmlBindingUtil.getTextContent(
                        elementList, "length"));
                fieldStructure.setMinInclusive(BlancoXmlBindingUtil
                        .getTextContent(elementList, "minInclusive"));
                fieldStructure.setMaxInclusive(BlancoXmlBindingUtil
                        .getTextContent(elementList, "maxInclusive"));
                fieldStructure.setPattern(BlancoXmlBindingUtil.getTextContent(
                        elementList, "pattern"));

                if (fieldStructure.getType() == null
                        || fieldStructure.getType().trim().length() == 0) {
                    throw new IllegalArgumentException(fMsg.getMbvoji02(
                            objClassStructure.getName(), fieldStructure
                                    .getName()));
                }

                objClassStructure.getFieldList().add(fieldStructure);
            }
        }

        return objClassStructure;
    }

    /**
     * 中間XMLファイル形式の「sheet」XMLエレメント(PHP書式)をパースして、バリューオブジェクト情報を取得します。
     *
     * @param argElementSheet
     *            中間XMLファイルの「sheet」XMLエレメント。
     * @return パースの結果得られたバリューオブジェクト情報。「name」が見つからなかった場合には nullを戻します。
     */
    public BlancoValueObjectKtClassStructure parseElementSheetPhp(
            final BlancoXmlElement argElementSheet,
            final Map<String, String> argClassList) {
        final BlancoValueObjectKtClassStructure objClassStructure = new BlancoValueObjectKtClassStructure();
        final List<BlancoXmlElement> listCommon = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        "blancovalueobjectphp-common");
        if (listCommon == null || listCommon.size() == 0) {
            // commonが無い場合にはスキップします。
            return null;
        }

        if (argClassList == null) {
            // classList が無い場合もスキップします
            System.out.println("### ERROR ### NO CLASS LIST DEFINED.");
            return null;
        }

        final BlancoXmlElement elementCommon = listCommon.get(0);
        objClassStructure.setName(BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name"));
        objClassStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                elementCommon, "package"));

        objClassStructure.setDescription(BlancoXmlBindingUtil.getTextContent(
                elementCommon, "description"));
        if (BlancoStringUtil.null2Blank(objClassStructure.getDescription())
                .length() > 0) {
            final String[] lines = BlancoNameUtil.splitString(objClassStructure
                    .getDescription(), '\n');
            for (int index = 0; index < lines.length; index++) {
                if (index == 0) {
                    objClassStructure.setDescription(lines[index]);
                } else {
                    // 複数行の description については、これを分割して格納します。
                    // ２行目からは、適切に文字参照エンコーディングが実施されているものと仮定します。
                    objClassStructure.getDescriptionList().add(lines[index]);
                }
            }
        }

        /* クラスの総称型に対応 */
        String classGenerics = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "generic");
        if (BlancoStringUtil.null2Blank(classGenerics).length() > 0) {
            objClassStructure.setGeneric(classGenerics);
        }


        /* クラスの annotation に対応 */
        String classAnnotation = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "annotation");
        if (BlancoStringUtil.null2Blank(classAnnotation).length() > 0) {
            objClassStructure.setAnnotationList(createAnnotaionList(classAnnotation));
        }

        objClassStructure.setAccess(BlancoXmlBindingUtil.getTextContent(
                elementCommon, "access"));
        objClassStructure.setFinal("true".equals(BlancoXmlBindingUtil
                .getTextContent(elementCommon, "final")));
        objClassStructure.setAbstract("true".equals(BlancoXmlBindingUtil
                .getTextContent(elementCommon, "abstract")));
        objClassStructure.setData("true".equals(BlancoXmlBindingUtil
                .getTextContent(elementCommon, "data")));
        objClassStructure.setGenerateToString("true"
                .equals(BlancoXmlBindingUtil.getTextContent(elementCommon,
                        "generateToString")));
        objClassStructure.setAdjustFieldName("true".equals(BlancoXmlBindingUtil
                .getTextContent(elementCommon, "adjustFieldName")));
        objClassStructure.setAdjustDefaultValue("true"
                .equals(BlancoXmlBindingUtil.getTextContent(elementCommon,
                        "adjustDefaultValue")));
        objClassStructure
                .setFieldList(new ArrayList<blanco.valueobjectkt.valueobject.BlancoValueObjectKtFieldStructure>());

        if (BlancoStringUtil.null2Blank(objClassStructure.getName()).trim()
                .length() == 0) {
            // 名前が無いものはスキップします。
            return null;
        }

        if (objClassStructure.getPackage() == null) {
            throw new IllegalArgumentException(fMsg
                    .getMbvoji01(objClassStructure.getName()));
        }

        final List<BlancoXmlElement> extendsList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        "blancovalueobjectphp-extends");
        if (extendsList != null && extendsList.size() != 0) {
            final BlancoXmlElement elementExtendsRoot = extendsList.get(0);
            String className = BlancoXmlBindingUtil.getTextContent(elementExtendsRoot, "name");
            String packageName = BlancoXmlBindingUtil.getTextContent(elementExtendsRoot, "package");
            if (packageName == null) {
            /*
             * このクラスのパッケージ名を探す
             */
                packageName = argClassList.get(className);
            }
            if (packageName != null) {
                className = packageName + "." + className;
                System.out.println("/* tueda */ Extends : " + className);
            }
            objClassStructure.setExtends(className);
        }

        /* 実装 */
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        "blancovalueobjectphp-implements");
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            final List<BlancoXmlElement> listInterfaceChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementInterfaceRoot, "interface");
            for (int index = 0; index < listInterfaceChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listInterfaceChildNodes
                        .get(index);

                final String interfaceName = BlancoXmlBindingUtil
                        .getTextContent(elementList, "name");
                if (interfaceName == null || interfaceName.trim().length() == 0) {
                    continue;
                }
                objClassStructure.getImplementsList().add(
                        BlancoXmlBindingUtil
                                .getTextContent(elementList, "name"));
            }
        }

        /* import の一覧作成 */
        final List<BlancoXmlElement> importList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, "blancovalueobjectphp-import");
        if (importList != null && importList.size() != 0) {
            final BlancoXmlElement elementImportRoot = importList.get(0);
            final List<BlancoXmlElement> listImportChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementImportRoot, "import");
            for (int index = 0; index < listImportChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listImportChildNodes
                        .get(index);

                final String importName = BlancoXmlBindingUtil
                        .getTextContent(elementList, "name");
                System.out.println("/* tueda */ import = " + importName);
                if (importName == null || importName.trim().length() == 0) {
                    continue;
                }
                objClassStructure.getImportList().add(
                        BlancoXmlBindingUtil
                                .getTextContent(elementList, "name"));
            }
        }

        final List<BlancoXmlElement> listList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, "blancovalueobjectphp-list");
        if (listList != null && listList.size() != 0) {
            final BlancoXmlElement elementListRoot = listList.get(0);
            final List<BlancoXmlElement> listChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementListRoot, "field");
            for (int index = 0; index < listChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listChildNodes.get(index);
                final BlancoValueObjectKtFieldStructure fieldStructure = new BlancoValueObjectKtFieldStructure();

                fieldStructure.setNo(BlancoXmlBindingUtil.getTextContent(
                        elementList, "no"));
                fieldStructure.setName(BlancoXmlBindingUtil.getTextContent(
                        elementList, "name"));
                if (fieldStructure.getName() == null
                        || fieldStructure.getName().trim().length() == 0) {
                    continue;
                }

                /*
                 * 型の取得．ここで Kotlin 風の型名に変えておく
                 */
                String phpType = BlancoXmlBindingUtil.getTextContent(elementList, "type");
                if (BlancoStringUtil.null2Blank(phpType).length() == 0) {
                    // 型は必須
                    throw new IllegalArgumentException(fMsg.getMbvoji04(
                            objClassStructure.getName(),
                            fieldStructure.getName()
                    ));

                }
                String kotlinType = parsePhpTypes(phpType, argClassList, false);
                fieldStructure.setType(kotlinType);

                /* Generic に対応 */
                String phpGeneric = BlancoXmlBindingUtil.getTextContent(elementList, "generic");
                if (BlancoStringUtil.null2Blank(phpGeneric).length() != 0) {
                    String kotlinGeneric = parsePhpTypes(phpGeneric, argClassList, true);
                    fieldStructure.setGeneric(kotlinGeneric);
                }

                /* method の annnotation に対応 */
                String methodAnnotation = BlancoXmlBindingUtil.getTextContent(elementList, "annotation");
                if (BlancoStringUtil.null2Blank(methodAnnotation).length() != 0) {
                    fieldStructure.setAnnotationList(createAnnotaionList(methodAnnotation));
                }

                // abstract に対応
                fieldStructure.setAbstract("true".equals(BlancoXmlBindingUtil
                        .getTextContent(elementList, "abstract")));
                // Nullable に対応
                fieldStructure.setNullable("true".equals(BlancoXmlBindingUtil
                        .getTextContent(elementList, "nullable")));
                // value に対応
                fieldStructure.setValue("true".equals(BlancoXmlBindingUtil
                        .getTextContent(elementList, "fixedValue")));
                // constructorArg に対応
                fieldStructure.setConstArg("true".equals(BlancoXmlBindingUtil
                        .getTextContent(elementList, "constructorArg")));

                fieldStructure.setDescription(BlancoXmlBindingUtil
                        .getTextContent(elementList, "description"));
                final String[] lines = BlancoNameUtil.splitString(
                        fieldStructure.getDescription(), '\n');
                for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                    if (indexLine == 0) {
                        fieldStructure.setDescription(lines[indexLine]);
                    } else {
                        // 複数行の description については、これを分割して格納します。
                        // ２行目からは、適切に文字参照エンコーディングが実施されているものと仮定します。
                        fieldStructure.getDescriptionList().add(
                                lines[indexLine]);
                    }
                }

                fieldStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                        elementList, "default"));
                fieldStructure.setDefaultKt(BlancoXmlBindingUtil.getTextContent(
                        elementList, "defaultKt"));

                fieldStructure.setMinLength(BlancoXmlBindingUtil
                        .getTextContent(elementList, "minLength"));
                fieldStructure.setMaxLength(BlancoXmlBindingUtil
                        .getTextContent(elementList, "maxLength"));
                fieldStructure.setLength(BlancoXmlBindingUtil.getTextContent(
                        elementList, "length"));
                fieldStructure.setMinInclusive(BlancoXmlBindingUtil
                        .getTextContent(elementList, "minInclusive"));
                fieldStructure.setMaxInclusive(BlancoXmlBindingUtil
                        .getTextContent(elementList, "maxInclusive"));
                fieldStructure.setPattern(BlancoXmlBindingUtil.getTextContent(
                        elementList, "pattern"));

                if (fieldStructure.getType() == null
                        || fieldStructure.getType().trim().length() == 0) {
                    throw new IllegalArgumentException(fMsg.getMbvoji02(
                            objClassStructure.getName(), fieldStructure
                                    .getName()));
                }

                objClassStructure.getFieldList().add(fieldStructure);
            }
        }

        return objClassStructure;
    }

    public static Map<String, String> createClassListFromSheets(final File[] argFileMeta) {
        Map<String, String> classList = new HashMap<String, String>();

        for (int index = 0; index < argFileMeta.length; index++) {
            File metaXmlSourceFile = argFileMeta[index];

            if (metaXmlSourceFile.getName().endsWith(".xml") == false) {
                continue;
            }

            final BlancoXmlDocument documentMeta = new BlancoXmlUnmarshaller()
                    .unmarshal(metaXmlSourceFile);
            if (documentMeta == null) {
                continue;
            }

            // ルートエレメントを取得します。
            final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                    .getDocumentElement(documentMeta);
            if (elementRoot == null) {
                // ルートエレメントが無い場合には処理中断します。
                continue;
            }

            // sheet(Excelシート)のリストを取得します。
            final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                    .getElementsByTagName(elementRoot, "sheet");


            for (BlancoXmlElement elementSheet : listSheet) {
            /*
             * Java以外の言語用に記述されたシートにも対応．
             */
                List<BlancoXmlElement> listCommon = null;
                for (String common : mapCommons.keySet()) {
                    listCommon = BlancoXmlBindingUtil
                            .getElementsByTagName(elementSheet,
                                    common);
                    if (listCommon.size() != 0) {
                        BlancoXmlElement elementCommon = listCommon.get(0);
                        classList.put(
                                BlancoXmlBindingUtil.getTextContent(elementCommon, "name"),
                                BlancoXmlBindingUtil.getTextContent(elementCommon, "package")
                        );

//                        System.out.println("/* tueda */ createClassList = " +
//                                BlancoXmlBindingUtil.getTextContent(elementCommon, "name") + " : " +
//                                BlancoXmlBindingUtil.getTextContent(elementCommon, "package"));
                        break;
                    }
                }
            }
        }

        return classList;
    }

    private List<String> createAnnotaionList(String annotations) {
        List<String> annotationList = new ArrayList<>();
        final String[] lines = BlancoNameUtil.splitString(annotations, '\n');
        StringBuffer sb = new StringBuffer();
        for (String line : lines) {
            if (line.startsWith("@")) {
                if (sb.length() > 0) {
                    annotationList.add(sb.toString());
                    sb = new StringBuffer();
                }
                line = line.substring(1);
            }
            sb.append(line + System.getProperty("line.separator", "\n"));
        }
        if (sb.length() > 0) {
            annotationList.add(sb.toString());
        }
        if (this.isVerbose()) {
            for (String ann : annotationList) {
                System.out.println("Ann: " + ann);
            }
        }
        return annotationList;
    }

    private String parsePhpTypes(String phpType, final Map<String, String> argClassList, boolean isGeneric) {
        String kotlinType = phpType;
        if (BlancoStringUtil.null2Blank(phpType).length() != 0) {
            if ("boolean".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Boolean";
            } else
            if ("integer".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Int";
            } else
            if ("double".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Double";
            } else
            if ("float".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Double";
            } else
            if ("string".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.String";
            } else
            if ("datetime".equalsIgnoreCase(phpType)) {
                kotlinType = "java.util.Date";
            } else
            if ("array".equalsIgnoreCase(phpType)) {
                if (isGeneric) {
                    throw new IllegalArgumentException("Cannot use array for Generics.");
                } else {
                    kotlinType = "kotlin.collections.ArrayList";
                }
            } else
            if ("object".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Any";
            } else {
                /* この名前の package を探す */
                String packageName = argClassList.get(phpType);
                if (packageName != null) {
                    kotlinType = packageName + "." + phpType;
                }

                /* その他はそのまま記述する */
                System.out.println("/* tueda */ Unknown php type: " + kotlinType);
            }
        }
        return kotlinType;
    }
}
