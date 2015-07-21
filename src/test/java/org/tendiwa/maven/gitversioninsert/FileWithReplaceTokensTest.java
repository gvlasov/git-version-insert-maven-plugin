/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, Georgy Vlasov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.tendiwa.maven.gitversioninsert;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Georgy Vlasov (suseika@tendiwa.org)
 * @version $stub$
 * @since 0.1
 */
public final class FileWithReplaceTokensTest {
    private Path tempFilePath;

    @Before
    public void setUp() throws Exception{
        this.tempFilePath = Files.createTempFile(
            FileWithReplaceTokensTest.class.getName(),
            "replacesToken"
        );
        final PrintWriter writer = new PrintWriter(tempFilePath.toFile());
        writer.print("Version 1");
        writer.close();
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(this.tempFilePath);
    }

    /**
     * {@link FileWithReplaceTokens} can replace a token in a file with some
     * text.
     */
    @Test
    public void replacesToken() throws Exception {
        new FileWithReplaceTokens(
            this.tempFilePath.toAbsolutePath()
        ).replaceToken("Version", "Replacement");
        MatcherAssert.assertThat(
            this.firstLineInFile(),
            Matchers.allOf(
                Matchers.containsString("Replacement"),
                Matchers.not(
                    Matchers.containsString("Version")
                )
            )
        );
    }

    private String firstLineInFile() throws Exception {
        return Files
            .readAllLines(this.tempFilePath, Charset.forName("UTF-8"))
            .iterator()
            .next();
    }
}
