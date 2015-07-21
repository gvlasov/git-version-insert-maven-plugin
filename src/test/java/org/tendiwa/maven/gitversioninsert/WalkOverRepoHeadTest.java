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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * @author Georgy Vlasov (suseika@tendiwa.org)
 * @version $stub$
 * @since 0.1
 */
public final class WalkOverRepoHeadTest {
    @Test
    public void walksOverRepoHeadTest() throws Exception{
        final WalkOverRepoHead walk = new WalkOverRepoHead(
            new TestRepo()
        );
        Collection<Path> paths = new ArrayList<>();
        while (walk.next()) {
            paths.add(
                Paths.get(walk.getPathString()).getFileName()
            );
        }
        MatcherAssert.assertThat(
            paths,
            Matchers.allOf(
                Matchers.contains(Paths.get("file1")),
                Matchers.contains(Paths.get("file2"))
            )
        );
    }
}
