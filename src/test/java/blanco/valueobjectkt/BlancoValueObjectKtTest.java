/*
 * blanco Framework
 * Copyright (C) 2004-2020 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.valueobjectkt;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import blanco.valueobjectkt.task.BlancoValueObjectKtProcessImpl;
import blanco.valueobjectkt.task.valueobject.BlancoValueObjectKtProcessInput;

/**
 * Generation test for Kotlin.
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoValueObjectKtTest {

    @Test
    public void testBlancoValueObjectKt() {
        BlancoValueObjectKtProcessInput input = new BlancoValueObjectKtProcessInput();
        input.setMetadir("meta/objects2");
        input.setEncoding("UTF-8");
        input.setSheetType("php");
        input.setTmpdir("tmpTest2");
        input.setTargetdir("sample/blanco");
        input.setTargetStyle("maven");
        input.setVerbose(true);
        input.setLineSeparator("LF");
        input.setPackageSuffix("blanco");
        input.setSerdeable(true);
//        input.setIgnoreUnknown(true);
        input.setNullableAnnotation(true);

        BlancoValueObjectKtProcessImpl imple = new BlancoValueObjectKtProcessImpl();
        try {
            imple.execute(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        input = new BlancoValueObjectKtProcessInput();
        input.setMetadir("meta/objects");
        input.setEncoding("UTF-8");
        input.setSheetType("php");
        input.setTmpdir("tmpTest");
        input.setTargetdir("sample/blanco");
        input.setTargetStyle("maven");
        input.setVerbose(true);
        input.setLineSeparator("LF");
        input.setPackageSuffix("blanco");
        input.setSearchTmpdir("tmpTest2");

        imple = new BlancoValueObjectKtProcessImpl();
        try {
            imple.execute(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
