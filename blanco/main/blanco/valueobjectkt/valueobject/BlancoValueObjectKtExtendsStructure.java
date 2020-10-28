package blanco.valueobjectkt.valueobject;

/**
 * バリューオブジェクトの継承をあらわすバリューオブジェクトクラス。このクラスの設定情報をもとに継承が自動生成されます。
 */
public class BlancoValueObjectKtExtendsStructure {
    /**
     * 項目番号。省略可能です。
     *
     * フィールド: [no]。
     */
    private String fNo;

    /**
     * 型名をパッケージ名のフル修飾付で指定します。必須項目です。
     *
     * フィールド: [type]。
     */
    private String fType;

    /**
     * 総称型名をパッケージ名のフル修飾付で指定します。必須項目です。
     *
     * フィールド: [generics]。
     */
    private String fGenerics;

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
     * フィールド [generics] の値を設定します。
     *
     * フィールドの説明: [総称型名をパッケージ名のフル修飾付で指定します。必須項目です。]。
     *
     * @param argGenerics フィールド[generics]に設定する値。
     */
    public void setGenerics(final String argGenerics) {
        fGenerics = argGenerics;
    }

    /**
     * フィールド [generics] の値を取得します。
     *
     * フィールドの説明: [総称型名をパッケージ名のフル修飾付で指定します。必須項目です。]。
     *
     * @return フィールド[generics]から取得した値。
     */
    public String getGenerics() {
        return fGenerics;
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
        buf.append("blanco.valueobjectkt.valueobject.BlancoValueObjectKtExtendsStructure[");
        buf.append("no=" + fNo);
        buf.append(",type=" + fType);
        buf.append(",generics=" + fGenerics);
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
    public void copyTo(final BlancoValueObjectKtExtendsStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoValueObjectKtExtendsStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fNo
        // Type: java.lang.String
        target.fNo = this.fNo;
        // Name: fType
        // Type: java.lang.String
        target.fType = this.fType;
        // Name: fGenerics
        // Type: java.lang.String
        target.fGenerics = this.fGenerics;
    }
}
