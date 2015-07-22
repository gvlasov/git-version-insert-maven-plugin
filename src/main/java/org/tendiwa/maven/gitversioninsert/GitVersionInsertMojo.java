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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Mojo for <pre>git-version-insert-maven-plugin:insert-version</pre>
 * @author Georgy Vlasov (suseika@tendiwa.org)
 * @version $version-stub$
 * @since 0.1
 */
@Mojo(name = "insert-version")
public class GitVersionInsertMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public void execute() {
        final WorkingDirectory repo = new WorkingDirectory(
            this.project.getBasedir().getAbsolutePath()
        );
        for (FileInGitWorkingTree file : repo.headFiles()) {
            new FileWithReplaceTokens(file.absolutePath())
                .replaceToken(
                    "$version-stub$",
                    file.lastRevision()
                );
        }
    }
}
