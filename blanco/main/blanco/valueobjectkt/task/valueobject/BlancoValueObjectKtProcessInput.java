package blanco.valueobjectkt.task.valueobject;

/**
 * An input value object class for the processing class [BlancoValueObjectKtProcess].
 */
public class BlancoValueObjectKtProcessInput {
    /**
     * Whether to run in verbose mode.
     *
     * フィールド: [verbose]。
     * デフォルト: [false]。
     */
    private boolean fVerbose = false;

    /**
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。
     *
     * フィールド: [metadir]。
     */
    private String fMetadir;

    /**
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。
     *
     * フィールド: [targetdir]。
     * デフォルト: [blanco]。
     */
    private String fTargetdir = "blanco";

    /**
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。
     *
     * フィールド: [tmpdir]。
     * デフォルト: [tmp]。
     */
    private String fTmpdir = "tmp";

    /**
     * 自動生成するソースファイルの文字エンコーディングを指定します。
     *
     * フィールド: [encoding]。
     */
    private String fEncoding;

    /**
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。
     *
     * フィールド: [xmlrootelement]。
     * デフォルト: [false]。
     */
    private boolean fXmlrootelement = false;

    /**
     * meta定義書が期待しているプログラミング言語を指定します
     *
     * フィールド: [sheetType]。
     * デフォルト: [java]。
     */
    private String fSheetType = "java";

    /**
     * 出力先フォルダの書式を指定します。&amp;lt;br&amp;gt;\nblanco: [targetdir]/main&amp;lt;br&amp;gt;\nmaven: [targetdir]/main/java&amp;lt;br&amp;gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)
     *
     * フィールド: [targetStyle]。
     * デフォルト: [blanco]。
     */
    private String fTargetStyle = "blanco";

    /**
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。
     *
     * フィールド: [lineSeparator]。
     * デフォルト: [LF]。
     */
    private String fLineSeparator = "LF";

    /**
     * 定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。
     *
     * フィールド: [packageSuffix]。
     */
    private String fPackageSuffix;

    /**
     * 定義書で指定されたパッケージ名を上書きします。
     *
     * フィールド: [overridePackage]。
     */
    private String fOverridePackage;

    /**
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。
     *
     * フィールド: [searchTmpdir]。
     */
    private String fSearchTmpdir;

    /**
     * フィールド [verbose] の値を設定します。
     *
     * フィールドの説明: [Whether to run in verbose mode.]。
     *
     * @param argVerbose フィールド[verbose]に設定する値。
     */
    public void setVerbose(final boolean argVerbose) {
        fVerbose = argVerbose;
    }

    /**
     * フィールド [verbose] の値を取得します。
     *
     * フィールドの説明: [Whether to run in verbose mode.]。
     * デフォルト: [false]。
     *
     * @return フィールド[verbose]から取得した値。
     */
    public boolean getVerbose() {
        return fVerbose;
    }

    /**
     * フィールド [metadir] の値を設定します。
     *
     * フィールドの説明: [メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]。
     *
     * @param argMetadir フィールド[metadir]に設定する値。
     */
    public void setMetadir(final String argMetadir) {
        fMetadir = argMetadir;
    }

    /**
     * フィールド [metadir] の値を取得します。
     *
     * フィールドの説明: [メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]。
     *
     * @return フィールド[metadir]から取得した値。
     */
    public String getMetadir() {
        return fMetadir;
    }

    /**
     * フィールド [targetdir] の値を設定します。
     *
     * フィールドの説明: [出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]。
     *
     * @param argTargetdir フィールド[targetdir]に設定する値。
     */
    public void setTargetdir(final String argTargetdir) {
        fTargetdir = argTargetdir;
    }

    /**
     * フィールド [targetdir] の値を取得します。
     *
     * フィールドの説明: [出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[targetdir]から取得した値。
     */
    public String getTargetdir() {
        return fTargetdir;
    }

    /**
     * フィールド [tmpdir] の値を設定します。
     *
     * フィールドの説明: [テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]。
     *
     * @param argTmpdir フィールド[tmpdir]に設定する値。
     */
    public void setTmpdir(final String argTmpdir) {
        fTmpdir = argTmpdir;
    }

    /**
     * フィールド [tmpdir] の値を取得します。
     *
     * フィールドの説明: [テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]。
     * デフォルト: [tmp]。
     *
     * @return フィールド[tmpdir]から取得した値。
     */
    public String getTmpdir() {
        return fTmpdir;
    }

    /**
     * フィールド [encoding] の値を設定します。
     *
     * フィールドの説明: [自動生成するソースファイルの文字エンコーディングを指定します。]。
     *
     * @param argEncoding フィールド[encoding]に設定する値。
     */
    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * フィールド [encoding] の値を取得します。
     *
     * フィールドの説明: [自動生成するソースファイルの文字エンコーディングを指定します。]。
     *
     * @return フィールド[encoding]から取得した値。
     */
    public String getEncoding() {
        return fEncoding;
    }

    /**
     * フィールド [xmlrootelement] の値を設定します。
     *
     * フィールドの説明: [XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]。
     *
     * @param argXmlrootelement フィールド[xmlrootelement]に設定する値。
     */
    public void setXmlrootelement(final boolean argXmlrootelement) {
        fXmlrootelement = argXmlrootelement;
    }

    /**
     * フィールド [xmlrootelement] の値を取得します。
     *
     * フィールドの説明: [XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]。
     * デフォルト: [false]。
     *
     * @return フィールド[xmlrootelement]から取得した値。
     */
    public boolean getXmlrootelement() {
        return fXmlrootelement;
    }

    /**
     * フィールド [sheetType] の値を設定します。
     *
     * フィールドの説明: [meta定義書が期待しているプログラミング言語を指定します]。
     *
     * @param argSheetType フィールド[sheetType]に設定する値。
     */
    public void setSheetType(final String argSheetType) {
        fSheetType = argSheetType;
    }

    /**
     * フィールド [sheetType] の値を取得します。
     *
     * フィールドの説明: [meta定義書が期待しているプログラミング言語を指定します]。
     * デフォルト: [java]。
     *
     * @return フィールド[sheetType]から取得した値。
     */
    public String getSheetType() {
        return fSheetType;
    }

    /**
     * フィールド [targetStyle] の値を設定します。
     *
     * フィールドの説明: [出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]。
     *
     * @param argTargetStyle フィールド[targetStyle]に設定する値。
     */
    public void setTargetStyle(final String argTargetStyle) {
        fTargetStyle = argTargetStyle;
    }

    /**
     * フィールド [targetStyle] の値を取得します。
     *
     * フィールドの説明: [出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[targetStyle]から取得した値。
     */
    public String getTargetStyle() {
        return fTargetStyle;
    }

    /**
     * フィールド [lineSeparator] の値を設定します。
     *
     * フィールドの説明: [行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]。
     *
     * @param argLineSeparator フィールド[lineSeparator]に設定する値。
     */
    public void setLineSeparator(final String argLineSeparator) {
        fLineSeparator = argLineSeparator;
    }

    /**
     * フィールド [lineSeparator] の値を取得します。
     *
     * フィールドの説明: [行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]。
     * デフォルト: [LF]。
     *
     * @return フィールド[lineSeparator]から取得した値。
     */
    public String getLineSeparator() {
        return fLineSeparator;
    }

    /**
     * フィールド [packageSuffix] の値を設定します。
     *
     * フィールドの説明: [定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。]。
     *
     * @param argPackageSuffix フィールド[packageSuffix]に設定する値。
     */
    public void setPackageSuffix(final String argPackageSuffix) {
        fPackageSuffix = argPackageSuffix;
    }

    /**
     * フィールド [packageSuffix] の値を取得します。
     *
     * フィールドの説明: [定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。]。
     *
     * @return フィールド[packageSuffix]から取得した値。
     */
    public String getPackageSuffix() {
        return fPackageSuffix;
    }

    /**
     * フィールド [overridePackage] の値を設定します。
     *
     * フィールドの説明: [定義書で指定されたパッケージ名を上書きします。]。
     *
     * @param argOverridePackage フィールド[overridePackage]に設定する値。
     */
    public void setOverridePackage(final String argOverridePackage) {
        fOverridePackage = argOverridePackage;
    }

    /**
     * フィールド [overridePackage] の値を取得します。
     *
     * フィールドの説明: [定義書で指定されたパッケージ名を上書きします。]。
     *
     * @return フィールド[overridePackage]から取得した値。
     */
    public String getOverridePackage() {
        return fOverridePackage;
    }

    /**
     * フィールド [searchTmpdir] の値を設定します。
     *
     * フィールドの説明: [import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]。
     *
     * @param argSearchTmpdir フィールド[searchTmpdir]に設定する値。
     */
    public void setSearchTmpdir(final String argSearchTmpdir) {
        fSearchTmpdir = argSearchTmpdir;
    }

    /**
     * フィールド [searchTmpdir] の値を取得します。
     *
     * フィールドの説明: [import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]。
     *
     * @return フィールド[searchTmpdir]から取得した値。
     */
    public String getSearchTmpdir() {
        return fSearchTmpdir;
    }

    /**
     * Gets the string representation of this value object.
     *
     * <P>Precautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the stringification process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @return String representation of a value object.
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.valueobjectkt.task.valueobject.BlancoValueObjectKtProcessInput[");
        buf.append("verbose=" + fVerbose);
        buf.append(",metadir=" + fMetadir);
        buf.append(",targetdir=" + fTargetdir);
        buf.append(",tmpdir=" + fTmpdir);
        buf.append(",encoding=" + fEncoding);
        buf.append(",xmlrootelement=" + fXmlrootelement);
        buf.append(",sheetType=" + fSheetType);
        buf.append(",targetStyle=" + fTargetStyle);
        buf.append(",lineSeparator=" + fLineSeparator);
        buf.append(",packageSuffix=" + fPackageSuffix);
        buf.append(",overridePackage=" + fOverridePackage);
        buf.append(",searchTmpdir=" + fSearchTmpdir);
        buf.append("]");
        return buf.toString();
    }

    /**
     * このバリューオブジェクトを指定のターゲットに複写します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ複写処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoValueObjectKtProcessInput target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoValueObjectKtProcessInput#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fVerbose
        // Type: boolean
        target.fVerbose = this.fVerbose;
        // Name: fMetadir
        // Type: java.lang.String
        target.fMetadir = this.fMetadir;
        // Name: fTargetdir
        // Type: java.lang.String
        target.fTargetdir = this.fTargetdir;
        // Name: fTmpdir
        // Type: java.lang.String
        target.fTmpdir = this.fTmpdir;
        // Name: fEncoding
        // Type: java.lang.String
        target.fEncoding = this.fEncoding;
        // Name: fXmlrootelement
        // Type: boolean
        target.fXmlrootelement = this.fXmlrootelement;
        // Name: fSheetType
        // Type: java.lang.String
        target.fSheetType = this.fSheetType;
        // Name: fTargetStyle
        // Type: java.lang.String
        target.fTargetStyle = this.fTargetStyle;
        // Name: fLineSeparator
        // Type: java.lang.String
        target.fLineSeparator = this.fLineSeparator;
        // Name: fPackageSuffix
        // Type: java.lang.String
        target.fPackageSuffix = this.fPackageSuffix;
        // Name: fOverridePackage
        // Type: java.lang.String
        target.fOverridePackage = this.fOverridePackage;
        // Name: fSearchTmpdir
        // Type: java.lang.String
        target.fSearchTmpdir = this.fSearchTmpdir;
    }
}
