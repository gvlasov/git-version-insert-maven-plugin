[![Build Status](https://travis-ci.org/Suseika/git-version-insert-maven-plugin.svg?branch=master)](https://travis-ci.org/Suseika/git-version-insert-maven-plugin)

# git-version-insert-maven-plugin

This Maven plugin can see the version when each individual file was changed
in a local Git repository, and insert that version in that file in
a place where it is specified with a `$version-stub$` stub. It determines the
version when a file was last modified based in Git tags in your local
repository under you Maven project's directory.

# Why would I use it?

The main intended use case is to automatically fill out `@version` tags in
javadocs.

# Plugin setup

Add the following to your `pom.xml`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.tendiwa</groupId>
            <artifactId>git-version-insert-maven-plugin</artifactId>
            <version>0.1</version>
        </plugin>
        ...
    </plugins>
    ...
</build>
```
# Example scenario

In your Maven project you have a file `src/main/java/org/myproject/App
.java` with this javadoc inside:

```java
/**
 * Application entry point
 * @version $version-stub$
 * @since 0.1
 */
```

And you also have this in file `src/test/java/org/myrpoject/AppTest.java`:

```java
/**
 * Test for application entry point.
 * @version $version-stub$
 * @since 0.2
 */
```

You have three tags in your repository indicating release versions: `0.1`, `0
.2` and `0.3`. Suppose the last commit the first file was changed in was
after the commit marked with `git tag 0.1`. And the second file was last
modified in version `0.3`. Then if you run `git-vesion-insert:insert-version`
goal in your Maven build, the contents of the files in your working directory
 will be replaced with the following:

`src/main/java/org/myproject/App.java`:

```java
/**
 * Application entry point
 * @version 0.1
 * @since 0.1
 */
```

`src/test/java/org/myrpoject/AppTest.java`:

```java
/**
 * Test for application entry point.
 * @version 0.3
 * @since 0.2
 */
```

# Using this plugin to create release artifacts

You use this plugin in a similar manner to [versions:set goal in
versions-maven-plugin](http://www.mojohaus.org/versions-maven-plugin/set-mojo
.html): unlike most plugins, these both operate not on your `target`
directory (that is cleaned with `mvn clean`), but on your sources.

Recommended workflow is:

- Under Git, keep files with `$version-stub$` stubs, not with actual versions
 already inserted by `git-version-insert` plugin;
- Make `git-version-insert:insert-versions` goal a part of your release
lifecycle (e.g. create a Maven profile that gets applied only when you
release the project);
- To release a new version (e.g. by deploying to Maven Central via a
Nexus), clone your git repository in a separate location and from there run
your release lifecycle.
