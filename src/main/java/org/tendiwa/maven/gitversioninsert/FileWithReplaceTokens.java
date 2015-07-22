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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Georgy Vlasov (suseika@tendiwa.org)
 * @version $stub$
 * @since 0.1
 */
final class FileWithReplaceTokens {
    private final Path absolutePath;

    FileWithReplaceTokens(Path pathToFile) {
        if (!pathToFile.toFile().exists()) {
            throw new IllegalArgumentException(
                "File " + pathToFile + " doesn't exist"
            );
        }
        this.absolutePath = pathToFile.toAbsolutePath();
    }

    public void replaceToken(String token, String replacement) {
        final Charset charset = StandardCharsets.UTF_8;
        try {
            final String content =
                new String(
                    Files.readAllBytes(this.absolutePath),
                    charset
                ).replace(token, replacement);
            Files.write(this.absolutePath, content.getBytes(charset));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
