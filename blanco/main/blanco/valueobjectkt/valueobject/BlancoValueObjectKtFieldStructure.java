package blanco.valueobjectkt.valueobject;

import java.util.List;

/**
 * バリューオブジェクトのフィールドをあらわすバリューオブジェクトクラス。このクラスの設定情報をもとにフィールドとセッター・ゲッターが自動生成されます。
 */
public class BlancoValueObjectKtFieldStructure {
    /**
     * 項目番号。省略可能です。
     *
     * フィールド: [no]。
     */
    private String fNo;

    /**
     * フィールド名を指定します。必須項目です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * 型名をパッケージ名のフル修飾付で指定します。必須項目です。
     *
     * フィールド: [type]。
     */
    private String fType;

    /**
     * 型が期待する総称型の具体的な型名を指定します．
     *
     * フィールド: [generic]。
     */
    private String fGeneric;

    /**
     * アノテーション文字列です
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<java.lang.String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * デフォルト値を指定します。
     *
     * フィールド: [default]。
     */
    private String fDefault;

    /**
     * Kotlin優先型名をパッケージ名のフル修飾付で指定します。
     *
     * フィールド: [typeKt]。
     */
    private String fTypeKt;

    /**
     * Kotlin優先型が期待する総称型の具体的な型名を指定します．
     *
     * フィールド: [genericKt]。
     */
    private String fGenericKt;

    /**
     * Kotlin優先アノテーション文字列です
     *
     * フィールド: [annotationListKt]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<java.lang.String> fAnnotationListKt = new java.util.ArrayList<java.lang.String>();

    /**
     * Kotlin優先デフォルト値を指定します。
     *
     * フィールド: [defaultKt]。
     */
    private String fDefaultKt;

    /**
     * abstract変数かどうか
     *
     * フィールド: [abstract]。
     * デフォルト: [false]。
     */
    private Boolean fAbstract = false;

    /**
     * nullable変数かどうか
     *
     * フィールド: [nullable]。
     * デフォルト: [false]。
     */
    private Boolean fNullable = false;

    /**
     * 必須変数かどうか。必須の場合は @NotNull アノテーションが付与されます。
     *
     * フィールド: [required]。
     * デフォルト: [false]。
     */
    private Boolean fRequired = false;

    /**
     * 変更不可変数かどうか
     *
     * フィールド: [value]。
     * デフォルト: [false]。
     */
    private Boolean fValue = false;

    /**
     * コンストラクタ引数かどう
     *
     * フィールド: [constArg]。
     * デフォルト: [false]。
     */
    private Boolean fConstArg = false;

    /**
     * フィールドの説明です。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * フィールドの補助説明です。文字参照エンコード済みの値を格納してください。
     *
     * フィールド: [descriptionList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<java.lang.String> fDescriptionList = new java.util.ArrayList<java.lang.String>();

    /**
     * (JavaDocへの反映のみ)正規表現を指定します。
     *
     * フィールド: [pattern]。
     */
    private String fPattern;

    /**
     * (JavaDocへの反映のみ)最小の長さを指定します。
     *
     * フィールド: [minLength]。
     */
    private String fMinLength;

    /**
     * (JavaDocへの反映のみ)最大の長さを指定します。
     *
     * フィールド: [maxLength]。
     */
    private String fMaxLength;

    /**
     * (JavaDocへの反映のみ)長さを指定します。
     *
     * フィールド: [length]。
     */
    private String fLength;

    /**
     * (JavaDocへの反映のみ)最小値を指定します。
     *
     * フィールド: [minInclusive]。
     */
    private String fMinInclusive;

    /**
     * (JavaDocへの反映のみ)最大値を指定します。
     *
     * フィールド: [maxInclusive]。
     */
    private String fMaxInclusive;

    /**
     * フィールド [no] の値を設定します。
     *
     * フィールドの説明: [項目番号。省略可能です。]。
     *
     * @param argNo フィールド[no]に設定する値。
     */
    public void setNo(final String argNo) {
        fNo = argNo;
    }

    /**
     * フィールド [no] の値を取得します。
     *
     * フィールドの説明: [項目番号。省略可能です。]。
     *
     * @return フィールド[no]から取得した値。
     */
    public String getNo() {
        return fNo;
    }

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [フィールド名を指定します。必須項目です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [フィールド名を指定します。必須項目です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [type] の値を設定します。
     *
     * フィールドの説明: [型名をパッケージ名のフル修飾付で指定します。必須項目です。]。
     *
     * @param argType フィールド[type]に設定する値。
     */
    public void setType(final String argType) {
        fType = argType;
    }

    /**
     * フィールド [type] の値を取得します。
     *
     * フィールドの説明: [型名をパッケージ名のフル修飾付で指定します。必須項目です。]。
     *
     * @return フィールド[type]から取得した値。
     */
    public String getType() {
        return fType;
    }

    /**
     * フィールド [generic] の値を設定します。
     *
     * フィールドの説明: [型が期待する総称型の具体的な型名を指定します．]。
     *
     * @param argGeneric フィールド[generic]に設定する値。
     */
    public void setGeneric(final String argGeneric) {
        fGeneric = argGeneric;
    }

    /**
     * フィールド [generic] の値を取得します。
     *
     * フィールドの説明: [型が期待する総称型の具体的な型名を指定します．]。
     *
     * @return フィールド[generic]から取得した値。
     */
    public String getGeneric() {
        return fGeneric;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [アノテーション文字列です]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<java.lang.String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [アノテーション文字列です]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<java.lang.String> getAnnotationList() {
        return fAnnotationList;
    }

    /**
     * フィールド [default] の値を設定します。
     *
     * フィールドの説明: [デフォルト値を指定します。]。
     *
     * @param argDefault フィールド[default]に設定する値。
     */
    public void setDefault(final String argDefault) {
        fDefault = argDefault;
    }

    /**
     * フィールド [default] の値を取得します。
     *
     * フィールドの説明: [デフォルト値を指定します。]。
     *
     * @return フィールド[default]から取得した値。
     */
    public String getDefault() {
        return fDefault;
    }

    /**
     * フィールド [typeKt] の値を設定します。
     *
     * フィールドの説明: [Kotlin優先型名をパッケージ名のフル修飾付で指定します。]。
     *
     * @param argTypeKt フィールド[typeKt]に設定する値。
     */
    public void setTypeKt(final String argTypeKt) {
        fTypeKt = argTypeKt;
    }

    /**
     * フィールド [typeKt] の値を取得します。
     *
     * フィールドの説明: [Kotlin優先型名をパッケージ名のフル修飾付で指定します。]。
     *
     * @return フィールド[typeKt]から取得した値。
     */
    public String getTypeKt() {
        return fTypeKt;
    }

    /**
     * フィールド [genericKt] の値を設定します。
     *
     * フィールドの説明: [Kotlin優先型が期待する総称型の具体的な型名を指定します．]。
     *
     * @param argGenericKt フィールド[genericKt]に設定する値。
     */
    public void setGenericKt(final String argGenericKt) {
        fGenericKt = argGenericKt;
    }

    /**
     * フィールド [genericKt] の値を取得します。
     *
     * フィールドの説明: [Kotlin優先型が期待する総称型の具体的な型名を指定します．]。
     *
     * @return フィールド[genericKt]から取得した値。
     */
    public String getGenericKt() {
        return fGenericKt;
    }

    /**
     * フィールド [annotationListKt] の値を設定します。
     *
     * フィールドの説明: [Kotlin優先アノテーション文字列です]。
     *
     * @param argAnnotationListKt フィールド[annotationListKt]に設定する値。
     */
    public void setAnnotationListKt(final List<java.lang.String> argAnnotationListKt) {
        fAnnotationListKt = argAnnotationListKt;
    }

    /**
     * フィールド [annotationListKt] の値を取得します。
     *
     * フィールドの説明: [Kotlin優先アノテーション文字列です]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationListKt]から取得した値。
     */
    public List<java.lang.String> getAnnotationListKt() {
        return fAnnotationListKt;
    }

    /**
     * フィールド [defaultKt] の値を設定します。
     *
     * フィールドの説明: [Kotlin優先デフォルト値を指定します。]。
     *
     * @param argDefaultKt フィールド[defaultKt]に設定する値。
     */
    public void setDefaultKt(final String argDefaultKt) {
        fDefaultKt = argDefaultKt;
    }

    /**
     * フィールド [defaultKt] の値を取得します。
     *
     * フィールドの説明: [Kotlin優先デフォルト値を指定します。]。
     *
     * @return フィールド[defaultKt]から取得した値。
     */
    public String getDefaultKt() {
        return fDefaultKt;
    }

    /**
     * フィールド [abstract] の値を設定します。
     *
     * フィールドの説明: [abstract変数かどうか]。
     *
     * @param argAbstract フィールド[abstract]に設定する値。
     */
    public void setAbstract(final Boolean argAbstract) {
        fAbstract = argAbstract;
    }

    /**
     * フィールド [abstract] の値を取得します。
     *
     * フィールドの説明: [abstract変数かどうか]。
     * デフォルト: [false]。
     *
     * @return フィールド[abstract]から取得した値。
     */
    public Boolean getAbstract() {
        return fAbstract;
    }

    /**
     * フィールド [nullable] の値を設定します。
     *
     * フィールドの説明: [nullable変数かどうか]。
     *
     * @param argNullable フィールド[nullable]に設定する値。
     */
    public void setNullable(final Boolean argNullable) {
        fNullable = argNullable;
    }

    /**
     * フィールド [nullable] の値を取得します。
     *
     * フィールドの説明: [nullable変数かどうか]。
     * デフォルト: [false]。
     *
     * @return フィールド[nullable]から取得した値。
     */
    public Boolean getNullable() {
        return fNullable;
    }

    /**
     * フィールド [required] の値を設定します。
     *
     * フィールドの説明: [必須変数かどうか。必須の場合は @NotNull アノテーションが付与されます。]。
     *
     * @param argRequired フィールド[required]に設定する値。
     */
    public void setRequired(final Boolean argRequired) {
        fRequired = argRequired;
    }

    /**
     * フィールド [required] の値を取得します。
     *
     * フィールドの説明: [必須変数かどうか。必須の場合は @NotNull アノテーションが付与されます。]。
     * デフォルト: [false]。
     *
     * @return フィールド[required]から取得した値。
     */
    public Boolean getRequired() {
        return fRequired;
    }

    /**
     * フィールド [value] の値を設定します。
     *
     * フィールドの説明: [変更不可変数かどうか]。
     *
     * @param argValue フィールド[value]に設定する値。
     */
    public void setValue(final Boolean argValue) {
        fValue = argValue;
    }

    /**
     * フィールド [value] の値を取得します。
     *
     * フィールドの説明: [変更不可変数かどうか]。
     * デフォルト: [false]。
     *
     * @return フィールド[value]から取得した値。
     */
    public Boolean getValue() {
        return fValue;
    }

    /**
     * フィールド [constArg] の値を設定します。
     *
     * フィールドの説明: [コンストラクタ引数かどう]。
     *
     * @param argConstArg フィールド[constArg]に設定する値。
     */
    public void setConstArg(final Boolean argConstArg) {
        fConstArg = argConstArg;
    }

    /**
     * フィールド [constArg] の値を取得します。
     *
     * フィールドの説明: [コンストラクタ引数かどう]。
     * デフォルト: [false]。
     *
     * @return フィールド[constArg]から取得した値。
     */
    public Boolean getConstArg() {
        return fConstArg;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [フィールドの説明です。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [フィールドの説明です。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [descriptionList] の値を設定します。
     *
     * フィールドの説明: [フィールドの補助説明です。文字参照エンコード済みの値を格納してください。]。
     *
     * @param argDescriptionList フィールド[descriptionList]に設定する値。
     */
    public void setDescriptionList(final List<java.lang.String> argDescriptionList) {
        fDescriptionList = argDescriptionList;
    }

    /**
     * フィールド [descriptionList] の値を取得します。
     *
     * フィールドの説明: [フィールドの補助説明です。文字参照エンコード済みの値を格納してください。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[descriptionList]から取得した値。
     */
    public List<java.lang.String> getDescriptionList() {
        return fDescriptionList;
    }

    /**
     * フィールド [pattern] の値を設定します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)正規表現を指定します。]。
     *
     * @param argPattern フィールド[pattern]に設定する値。
     */
    public void setPattern(final String argPattern) {
        fPattern = argPattern;
    }

    /**
     * フィールド [pattern] の値を取得します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)正規表現を指定します。]。
     *
     * @return フィールド[pattern]から取得した値。
     */
    public String getPattern() {
        return fPattern;
    }

    /**
     * フィールド [minLength] の値を設定します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)最小の長さを指定します。]。
     *
     * @param argMinLength フィールド[minLength]に設定する値。
     */
    public void setMinLength(final String argMinLength) {
        fMinLength = argMinLength;
    }

    /**
     * フィールド [minLength] の値を取得します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)最小の長さを指定します。]。
     *
     * @return フィールド[minLength]から取得した値。
     */
    public String getMinLength() {
        return fMinLength;
    }

    /**
     * フィールド [maxLength] の値を設定します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)最大の長さを指定します。]。
     *
     * @param argMaxLength フィールド[maxLength]に設定する値。
     */
    public void setMaxLength(final String argMaxLength) {
        fMaxLength = argMaxLength;
    }

    /**
     * フィールド [maxLength] の値を取得します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)最大の長さを指定します。]。
     *
     * @return フィールド[maxLength]から取得した値。
     */
    public String getMaxLength() {
        return fMaxLength;
    }

    /**
     * フィールド [length] の値を設定します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)長さを指定します。]。
     *
     * @param argLength フィールド[length]に設定する値。
     */
    public void setLength(final String argLength) {
        fLength = argLength;
    }

    /**
     * フィールド [length] の値を取得します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)長さを指定します。]。
     *
     * @return フィールド[length]から取得した値。
     */
    public String getLength() {
        return fLength;
    }

    /**
     * フィールド [minInclusive] の値を設定します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)最小値を指定します。]。
     *
     * @param argMinInclusive フィールド[minInclusive]に設定する値。
     */
    public void setMinInclusive(final String argMinInclusive) {
        fMinInclusive = argMinInclusive;
    }

    /**
     * フィールド [minInclusive] の値を取得します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)最小値を指定します。]。
     *
     * @return フィールド[minInclusive]から取得した値。
     */
    public String getMinInclusive() {
        return fMinInclusive;
    }

    /**
     * フィールド [maxInclusive] の値を設定します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)最大値を指定します。]。
     *
     * @param argMaxInclusive フィールド[maxInclusive]に設定する値。
     */
    public void setMaxInclusive(final String argMaxInclusive) {
        fMaxInclusive = argMaxInclusive;
    }

    /**
     * フィールド [maxInclusive] の値を取得します。
     *
     * フィールドの説明: [(JavaDocへの反映のみ)最大値を指定します。]。
     *
     * @return フィールド[maxInclusive]から取得した値。
     */
    public String getMaxInclusive() {
        return fMaxInclusive;
    }

    /**
     * このバリューオブジェクトの文字列表現を取得します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ文字列化の処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @return バリューオブジェクトの文字列表現。
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.valueobjectkt.valueobject.BlancoValueObjectKtFieldStructure[");
        buf.append("no=" + fNo);
        buf.append(",name=" + fName);
        buf.append(",type=" + fType);
        buf.append(",generic=" + fGeneric);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",default=" + fDefault);
        buf.append(",typeKt=" + fTypeKt);
        buf.append(",genericKt=" + fGenericKt);
        buf.append(",annotationListKt=" + fAnnotationListKt);
        buf.append(",defaultKt=" + fDefaultKt);
        buf.append(",abstract=" + fAbstract);
        buf.append(",nullable=" + fNullable);
        buf.append(",required=" + fRequired);
        buf.append(",value=" + fValue);
        buf.append(",constArg=" + fConstArg);
        buf.append(",description=" + fDescription);
        buf.append(",descriptionList=" + fDescriptionList);
        buf.append(",pattern=" + fPattern);
        buf.append(",minLength=" + fMinLength);
        buf.append(",maxLength=" + fMaxLength);
        buf.append(",length=" + fLength);
        buf.append(",minInclusive=" + fMinInclusive);
        buf.append(",maxInclusive=" + fMaxInclusive);
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
    public void copyTo(final BlancoValueObjectKtFieldStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoValueObjectKtFieldStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fNo
        // Type: java.lang.String
        target.fNo = this.fNo;
        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fType
        // Type: java.lang.String
        target.fType = this.fType;
        // Name: fGeneric
        // Type: java.lang.String
        target.fGeneric = this.fGeneric;
        // Name: fAnnotationList
        // Type: java.util.List
        if (this.fAnnotationList != null) {
            final java.util.Iterator<java.lang.String> iterator = this.fAnnotationList.iterator();
            for (; iterator.hasNext();) {
                java.lang.String loopSource = iterator.next();
                java.lang.String loopTarget = null;
                loopTarget = loopSource;
                target.fAnnotationList.add(loopTarget);
            }
        }
        // Name: fDefault
        // Type: java.lang.String
        target.fDefault = this.fDefault;
        // Name: fTypeKt
        // Type: java.lang.String
        target.fTypeKt = this.fTypeKt;
        // Name: fGenericKt
        // Type: java.lang.String
        target.fGenericKt = this.fGenericKt;
        // Name: fAnnotationListKt
        // Type: java.util.List
        if (this.fAnnotationListKt != null) {
            final java.util.Iterator<java.lang.String> iterator = this.fAnnotationListKt.iterator();
            for (; iterator.hasNext();) {
                java.lang.String loopSource = iterator.next();
                java.lang.String loopTarget = null;
                loopTarget = loopSource;
                target.fAnnotationListKt.add(loopTarget);
            }
        }
        // Name: fDefaultKt
        // Type: java.lang.String
        target.fDefaultKt = this.fDefaultKt;
        // Name: fAbstract
        // Type: java.lang.Boolean
        target.fAbstract = this.fAbstract;
        // Name: fNullable
        // Type: java.lang.Boolean
        target.fNullable = this.fNullable;
        // Name: fRequired
        // Type: java.lang.Boolean
        target.fRequired = this.fRequired;
        // Name: fValue
        // Type: java.lang.Boolean
        target.fValue = this.fValue;
        // Name: fConstArg
        // Type: java.lang.Boolean
        target.fConstArg = this.fConstArg;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fDescriptionList
        // Type: java.util.List
        if (this.fDescriptionList != null) {
            final java.util.Iterator<java.lang.String> iterator = this.fDescriptionList.iterator();
            for (; iterator.hasNext();) {
                java.lang.String loopSource = iterator.next();
                java.lang.String loopTarget = null;
                loopTarget = loopSource;
                target.fDescriptionList.add(loopTarget);
            }
        }
        // Name: fPattern
        // Type: java.lang.String
        target.fPattern = this.fPattern;
        // Name: fMinLength
        // Type: java.lang.String
        target.fMinLength = this.fMinLength;
        // Name: fMaxLength
        // Type: java.lang.String
        target.fMaxLength = this.fMaxLength;
        // Name: fLength
        // Type: java.lang.String
        target.fLength = this.fLength;
        // Name: fMinInclusive
        // Type: java.lang.String
        target.fMinInclusive = this.fMinInclusive;
        // Name: fMaxInclusive
        // Type: java.lang.String
        target.fMaxInclusive = this.fMaxInclusive;
    }
}
