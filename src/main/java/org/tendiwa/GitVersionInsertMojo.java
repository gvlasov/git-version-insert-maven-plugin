package org.tendiwa;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "insert-version")
public class GitVersionInsertMojo extends AbstractMojo {
    public void execute() {
        this.getLog().info("Hello world");
    }
}
