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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Working directory of a git repository.
 * @author Georgy Vlasov (suseika@tendiwa.org)
 * @version $stub$
 * @since 0.1
 */
final class WorkingDirectory {
    public static final int ESTIMATED_NUMBER_OF_FILES = 100;

    private final Git git;

    /**
     * @param path Path to working directory created when you
     * <pre>git clone</pre> a repository.
     */
    WorkingDirectory(String path) {
        try {
            this.git = new Git(
                new FileRepository(
                    path + "/.git"
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return All files in a tree under HEAD ref in this repo.
     */
    public Collection<FileInGitWorkingTree> headFiles() {
        final Collection<FileInGitWorkingTree> files =
            new ArrayList<>(WorkingDirectory.ESTIMATED_NUMBER_OF_FILES);
        final TreeWalk walk = new WalkOverRepoHead(this.git.getRepository());
        try {
            while (walk.next()) {
                files.add(
                    new FileInGitWorkingTree(
                        this.git,
                        walk.getPathString()
                    )
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }
}
