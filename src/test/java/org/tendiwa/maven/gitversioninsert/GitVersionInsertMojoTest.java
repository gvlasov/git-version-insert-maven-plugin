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

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Georgy Vlasov (suseika@tendiwa.org)
 * @version $tendiwa-version$
 * @since 0.1
 */
public final class GitVersionInsertMojoTest {
    public static final String BASEDIRPATH = "target/generated-test-resources/fake-maven-git";

    @Rule
    public final TestResources resources = new TestResources();

    @Rule
    public final TestMavenRuntime maven = new TestMavenRuntime();

    /**
     * Replaces a token in each file with that file's last version tag name.
     */
    @Test
    public void replacesToken() throws Exception {
        File basedir = this.resources.getBasedir(
            GitVersionInsertMojoTest.BASEDIRPATH
        );
        this.maven.executeMojo(
            basedir,
            "insert-version"
        );
        MatcherAssert.assertThat(
            this.fileToString(
                BASEDIRPATH + "/src/main/java/org/tendiwa/fake/App.java"
            ),
            Matchers.containsString("@version 0.1")
        );
        MatcherAssert.assertThat(
            this.fileToString(
                BASEDIRPATH + "/src/main/test/org/tendiwa/fake/AppTest.java"
            ),
            Matchers.containsString("@version 0.2")
        );
    }

    /**
     * Any token to replace can be set.
     */
    @Ignore
    @Test
    public void canConfigureToken() throws Exception {
        File basedir = this.resources.getBasedir(
            GitVersionInsertMojoTest.BASEDIRPATH
        );
        this.maven.executeMojo(
            basedir,
            "insert-version",
            TestMavenRuntime.newParameter(
                "token",
                "App"
            )
        );
        MatcherAssert.assertThat(
            this.fileToString(
                BASEDIRPATH + "/src/main/java/org/tendiwa/fake/App.java"
            ),
            Matchers.containsString("class 0.1")
        );
        MatcherAssert.assertThat(
            this.fileToString(
                BASEDIRPATH + "/src/test/java/org/tendiwa/fake/AppTest.java"
            ),
            Matchers.containsString("class 0.2")
        );
    }



    /**
     * Reads a UTF-8 file into a string.
     * @param filePath Path to a file.
     * @return String with file's content.
     */
    private String fileToString(String filePath) throws IOException {
        return new String(
            Files.readAllBytes(
                Paths.get(filePath)
            )
        );
    }
}
