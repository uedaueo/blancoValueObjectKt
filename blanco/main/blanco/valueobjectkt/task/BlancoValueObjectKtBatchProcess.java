package blanco.valueobjectkt.task;

import java.io.IOException;

import blanco.valueobjectkt.task.valueobject.BlancoValueObjectKtProcessInput;

/**
 * Batch process class [BlancoValueObjectKtBatchProcess].
 *
 * <P>Example of a batch processing call.</P>
 * <code>
 * java -classpath (classpath) blanco.valueobjectkt.task.BlancoValueObjectKtBatchProcess -help
 * </code>
 */
public class BlancoValueObjectKtBatchProcess {
    /**
     * Normal end.
     */
    public static final int END_SUCCESS = 0;

    /**
     * Termination due to abnormal input. In the case that java.lang.IllegalArgumentException is raised internally.
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * Termination due to I/O exception. In the case that java.io.IOException is raised internally.
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * Abnormal end. In the case that batch process fails to start or java.lang.Error or java.lang.RuntimeException is raised internally.
     */
    public static final int END_ERROR = 9;

    /**
     * The entry point when executed from the command line.
     *
     * @param args Agruments inherited from the console.
     */
    public static final void main(final String[] args) {
        final BlancoValueObjectKtBatchProcess batchProcess = new BlancoValueObjectKtBatchProcess();

        // Arguments for batch process.
        final BlancoValueObjectKtProcessInput input = new BlancoValueObjectKtProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldMetadirProcessed = false;

        // Parses command line arguments.
        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            if (arg.startsWith("-verbose=")) {
                input.setVerbose(Boolean.valueOf(arg.substring(9)).booleanValue());
            } else if (arg.startsWith("-metadir=")) {
                input.setMetadir(arg.substring(9));
                isFieldMetadirProcessed = true;
            } else if (arg.startsWith("-targetdir=")) {
                input.setTargetdir(arg.substring(11));
            } else if (arg.startsWith("-tmpdir=")) {
                input.setTmpdir(arg.substring(8));
            } else if (arg.startsWith("-encoding=")) {
                input.setEncoding(arg.substring(10));
            } else if (arg.startsWith("-xmlrootelement=")) {
                input.setXmlrootelement(Boolean.valueOf(arg.substring(16)).booleanValue());
            } else if (arg.startsWith("-sheetType=")) {
                input.setSheetType(arg.substring(11));
            } else if (arg.startsWith("-targetStyle=")) {
                input.setTargetStyle(arg.substring(13));
            } else if (arg.startsWith("-lineSeparator=")) {
                input.setLineSeparator(arg.substring(15));
            } else if (arg.startsWith("-packageSuffix=")) {
                input.setPackageSuffix(arg.substring(15));
            } else if (arg.startsWith("-overridePackage=")) {
                input.setOverridePackage(arg.substring(17));
            } else if (arg.startsWith("-searchTmpdir=")) {
                input.setSearchTmpdir(arg.substring(14));
            } else if (arg.startsWith("-serdeable=")) {
                input.setSerdeable(Boolean.valueOf(arg.substring(11)).booleanValue());
            } else if (arg.startsWith("-ignoreUnknown=")) {
                input.setIgnoreUnknown(Boolean.valueOf(arg.substring(15)).booleanValue());
            } else if (arg.startsWith("-nullableAnnotation=")) {
                input.setNullableAnnotation(Boolean.valueOf(arg.substring(20)).booleanValue());
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("BlancoValueObjectKtBatchProcess: The input parameter[" + arg + "] was ignored.");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldMetadirProcessed == false) {
            System.out.println("BlancoValueObjectKtBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // Returns the exit code.
        // Note: Please note that calling System.exit().
        System.exit(retCode);
    }

    /**
     * A method to describe the specific batch processing contents.
     *
     * This method is used to describe the actual process.
     *
     * @param input Input parameters for batch process.
     * @return The exit code for batch process. Returns one of the values END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public int process(final BlancoValueObjectKtProcessInput input) throws IOException, IllegalArgumentException {
        // Checks the input parameters.
        validateInput(input);

        // If you get a compile error at this point, You may be able to solve it by implementing a BlancoValueObjectKtProcess interface and creating an BlancoValueObjectKtProcessImpl class in package blanco.valueobjectkt.task.
        final BlancoValueObjectKtProcess process = new BlancoValueObjectKtProcessImpl();

        // Executes the main body of the process.
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * The entry point for instantiating a class and running a batch.
     *
     * This method provides the following specifications.
     * <ul>
     * <li>Checks the contents of the input parameters of the method.
     * <li>Catches exceptions such as IllegalArgumentException, RuntimeException, Error, etc. and converts them to return values.
     * </ul>
     *
     * @param input Input parameters for batch process.
     * @return The exit code for batch process. Returns one of the values END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public final int execute(final BlancoValueObjectKtProcessInput input) throws IllegalArgumentException {
        try {
            // Executes the main body of the batch process.
            int retCode = process(input);

            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("BlancoValueObjectKtBatchProcess: An input exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("BlancoValueObjectKtBatchProcess: An I/O exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("BlancoValueObjectKtBatchProcess: A runtime exception has occurred. Abort the batch process.:" + ex.toString());
            ex.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        } catch (Error er) {
            System.out.println("BlancoValueObjectKtBatchProcess: A runtime exception has occurred. Abort the batch process.:" + er.toString());
            er.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        }
    }

    /**
     * A method to show an explanation of how to use this batch processing class on the stdout.
     */
    public static final void usage() {
        System.out.println("BlancoValueObjectKtBatchProcess: Usage:");
        System.out.println("  java blanco.valueobjectkt.task.BlancoValueObjectKtBatchProcess -verbose=value1 -metadir=value2 -targetdir=value3 -tmpdir=value4 -encoding=value5 -xmlrootelement=value6 -sheetType=value7 -targetStyle=value8 -lineSeparator=value9 -packageSuffix=value10 -overridePackage=value11 -searchTmpdir=value12 -serdeable=value13 -ignoreUnknown=value14 -nullableAnnotation=value15");
        System.out.println("    -verbose");
        System.out.println("      explanation[Whether to run in verbose mode.]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -metadir");
        System.out.println("      explanation[メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -targetdir");
        System.out.println("      explanation[出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -tmpdir");
        System.out.println("      explanation[テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]");
        System.out.println("      type[string]");
        System.out.println("      default value[tmp]");
        System.out.println("    -encoding");
        System.out.println("      explanation[自動生成するソースファイルの文字エンコーディングを指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -xmlrootelement");
        System.out.println("      explanation[XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -sheetType");
        System.out.println("      explanation[meta定義書が期待しているプログラミング言語を指定します]");
        System.out.println("      type[string]");
        System.out.println("      default value[java]");
        System.out.println("    -targetStyle");
        System.out.println("      explanation[出力先フォルダの書式を指定します。<br>\nblanco: [targetdir]/main<br>\nmaven: [targetdir]/main/java<br>\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -lineSeparator");
        System.out.println("      explanation[行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]");
        System.out.println("      type[string]");
        System.out.println("      default value[LF]");
        System.out.println("    -packageSuffix");
        System.out.println("      explanation[定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -overridePackage");
        System.out.println("      explanation[定義書で指定されたパッケージ名を上書きします。]");
        System.out.println("      type[string]");
        System.out.println("    -searchTmpdir");
        System.out.println("      explanation[import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]");
        System.out.println("      type[string]");
        System.out.println("    -serdeable");
        System.out.println("      explanation[電文クラスに@Serdeableアノテーションを付与します。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -ignoreUnknown");
        System.out.println("      explanation[電文クラスに@JsonIgnoreProperties(ignoreUnknown = true)アノテーションを付与します。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -nullableAnnotation");
        System.out.println("      explanation[「必須」が指定されていないパラメータに@Nullableアノテーションを強制します]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -? , -help");
        System.out.println("      explanation[show the usage.]");
    }

    /**
     * A method to check the validity of input parameters for this batch processing class.
     *
     * @param input Input parameters for batch process.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public void validateInput(final BlancoValueObjectKtProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: Failed to start the process. The input parameter[input] was given as null.");
        }
        if (input.getMetadir() == null) {
            throw new IllegalArgumentException("BlancoValueObjectKtBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
        }
    }
}
