package blanco.valueobjectkt;

import blanco.cg.BlancoCgSupportedLang;
import blanco.valueobjectkt.resourcebundle.BlancoValueObjectKtResourceBundle;
import blanco.valueobjectkt.task.valueobject.BlancoValueObjectKtProcessInput;
import blanco.xml.bind.BlancoXmlBindingUtil;
import blanco.xml.bind.BlancoXmlUnmarshaller;
import blanco.xml.bind.valueobject.BlancoXmlDocument;
import blanco.xml.bind.valueobject.BlancoXmlElement;

import java.io.File;
import java.util.*;

public class BlancoValueObjectKtUtil {

    public static boolean isVerbose = false;
    /**
     * SimpleClass名をキーに、パッケージ名を保持
     */
    public static Map<String, String> packageMap = new HashMap<>();

    /**
     * blancoValueObjectのリソースバンドルオブジェクト。
     */
    public final static BlancoValueObjectKtResourceBundle fBundle = new BlancoValueObjectKtResourceBundle();

    public static Map<String, Integer> mapCommons = new HashMap<String, Integer>() {
        {put(fBundle.getMeta2xmlElementCommon(), BlancoCgSupportedLang.JAVA);}
        {put(fBundle.getMeta2xmlElementCommonCs(), BlancoCgSupportedLang.CS);}
        {put(fBundle.getMeta2xmlElementCommonJs(), BlancoCgSupportedLang.JS);}
        {put(fBundle.getMeta2xmlElementCommonVb(), BlancoCgSupportedLang.VB);}
        {put(fBundle.getMeta2xmlElementCommonPhp(), BlancoCgSupportedLang.PHP);}
        {put(fBundle.getMeta2xmlElementCommonRuby(), BlancoCgSupportedLang.RUBY);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.PYTHON);}
    };

    public static Map<String, String> processValueObjects(final BlancoValueObjectKtProcessInput input) {
        if (isVerbose) {
            System.out.println("BlancoValueObjectKtUtil : processValueObjects start !");
        }

        /* tmpdir はユニーク */
        String baseTmpdir = input.getTmpdir();
        /* searchTmpdir はカンマ区切り */
        String tmpTmpdirs = input.getSearchTmpdir();
        List<String> searchTmpdirList = null;
        if (tmpTmpdirs != null && !tmpTmpdirs.equals(baseTmpdir)) {
            String[] searchTmpdirs = tmpTmpdirs.split(",");
            searchTmpdirList = new ArrayList<>(Arrays.asList(searchTmpdirs));
        }
        if (searchTmpdirList == null) {
            searchTmpdirList = new ArrayList<>();
        }
        searchTmpdirList.add(baseTmpdir);

        for (String tmpdir : searchTmpdirList) {
            // XML化された中間ファイルから情報を読み込む
            final File[] fileMeta = new File(tmpdir
                    + BlancoValueObjectKtConstants.TARGET_SUBDIRECTORY)
                    .listFiles();
            searchTmpdir(fileMeta, input.getPackageSuffix(), input.getOverridePackage());
        }

        return packageMap;
    }

    public static Map<String, String> searchTmpdir(final File[] argFileMeta, String packageSuffix, String overridePackage) {
        Map<String, String> classList = new HashMap<>();

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

                        // パッケージ名の置き換えオプションが指定されていれば置き換え
                        // Suffix があればそちらが優先です。
                        String myPackage = BlancoXmlBindingUtil.getTextContent(elementCommon, "package");

                        if (packageSuffix != null && packageSuffix.length() > 0) {
                            myPackage = myPackage + "." + packageSuffix;
                        } else if (overridePackage != null && overridePackage.length() > 0) {
                            myPackage = overridePackage;
                        }

                        classList.put(
                                BlancoXmlBindingUtil.getTextContent(elementCommon, "name"),
                                myPackage
                        );

//                        System.out.println("/* tueda */ createClassList = " +
//                                BlancoXmlBindingUtil.getTextContent(elementCommon, "name") + " : " +
//                                BlancoXmlBindingUtil.getTextContent(elementCommon, "package"));
                        break;
                    }
                }
            }
        }

        packageMap.putAll(classList);
        return packageMap;
    }

    /**
     * Make canonical classname into Simple.
     *
     * @param argClassNameCanon
     * @return simpleName
     */
    static public String getSimpleClassName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        String simpleName = "";
        final int findLastDot = argClassNameCanon.lastIndexOf('.');
        if (findLastDot == -1) {
            simpleName = argClassNameCanon;
        } else if (findLastDot != argClassNameCanon.length() - 1) {
            simpleName = argClassNameCanon.substring(findLastDot + 1);
        }
        return simpleName;
    }

    /**
     * Make canonical classname into packageName
     *
     * @param argClassNameCanon
     * @return
     */
    static public String getPackageName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        String simpleName = "";
        final int findLastDot = argClassNameCanon.lastIndexOf('.');
        if (findLastDot > 0) {
            simpleName = argClassNameCanon.substring(0, findLastDot);
        }
        return simpleName;
    }
}
