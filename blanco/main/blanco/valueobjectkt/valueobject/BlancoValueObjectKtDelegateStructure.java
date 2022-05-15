package blanco.valueobjectkt.valueobject;

import java.util.List;

/**
 * バリューオブジェクトの委譲をあらわすバリューオブジェクトクラス。このクラスの設定情報をもとにフィールドとセッター・ゲッターが自動生成されます。
 */
public class BlancoValueObjectKtDelegateStructure {
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
    private List<String> fDescriptionList = new java.util.ArrayList<java.lang.String>();

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
    public void setDescriptionList(final List<String> argDescriptionList) {
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
    public List<String> getDescriptionList() {
        return fDescriptionList;
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
        buf.append("blanco.valueobjectkt.valueobject.BlancoValueObjectKtDelegateStructure[");
        buf.append("no=" + fNo);
        buf.append(",name=" + fName);
        buf.append(",type=" + fType);
        buf.append(",generic=" + fGeneric);
        buf.append(",description=" + fDescription);
        buf.append(",descriptionList=" + fDescriptionList);
        buf.append("]");
        return buf.toString();
    }

    /**
     * Copies this value object to the specified target.
     *
     * <P>Cautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the copying process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoValueObjectKtDelegateStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoValueObjectKtDelegateStructure#copyTo(target): argument 'target' is null");
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
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fDescriptionList
        // Type: java.util.List
        // Field[fDescriptionList] is an unsupported type[java.util.Listjava.lang.String].
    }
}
