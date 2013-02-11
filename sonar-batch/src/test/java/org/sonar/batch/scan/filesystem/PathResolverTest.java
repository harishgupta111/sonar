/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.batch.scan.filesystem;

import org.apache.commons.io.FilenameUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class PathResolverTest {
  @Rule
  public TemporaryFolder temp = new TemporaryFolder();

  @Test
  public void get_file_by_relative_path() throws IOException {
    PathResolver resolver = new PathResolver();
    File rootDir = temp.newFolder();
    File file = resolver.relativeFile(rootDir, "org/foo/Bar.java");
    assertThat(file.getName()).isEqualTo("Bar.java");
    assertThat(FilenameUtils.separatorsToUnix(file.getCanonicalPath())).endsWith("org/foo/Bar.java");
    assertThat(file.getParentFile().getParentFile().getParentFile().getCanonicalPath()).isEqualTo(rootDir.getCanonicalPath());
  }

  @Test
  public void get_file_by_absolute_path() throws IOException {
    PathResolver resolver = new PathResolver();
    File rootDir = temp.newFolder();
    File file = resolver.relativeFile(rootDir, new File(rootDir, "org/foo/Bar.java").getAbsolutePath());
    assertThat(file.getName()).isEqualTo("Bar.java");
    assertThat(FilenameUtils.separatorsToUnix(file.getCanonicalPath())).endsWith("org/foo/Bar.java");
    assertThat(file.getParentFile().getParentFile().getParentFile().getCanonicalPath()).isEqualTo(rootDir.getCanonicalPath());
  }

  @Test
  public void get_files_by_relative_paths() throws IOException {
    PathResolver resolver = new PathResolver();
    File rootDir = temp.newFolder();
    List<File> files = resolver.relativeFiles(rootDir, Arrays.asList("org/foo/Bar.java", "org/hello/World.java"));
    assertThat(files).hasSize(2);
    for (File file : files) {
      assertThat(file.getName()).endsWith(".java");
      assertThat(file.getParentFile().getParentFile().getParentFile().getCanonicalPath()).isEqualTo(rootDir.getCanonicalPath());
    }
  }

  @Test
  public void get_relative_path() throws IOException {
    PathResolver resolver = new PathResolver();
    File rootDir = temp.newFolder();
    File org = new File(rootDir, "org");
    File hello = new File(org, "hello");
    File world = new File(hello, "World.java");

    assertThat(resolver.relativePath(rootDir, world)).isEqualTo("org/hello/World.java");
  }

  @Test
  public void null_relative_path_when_file_is_not_in_dir() throws IOException {
    PathResolver resolver = new PathResolver();
    File rootDir = temp.newFolder();

    assertThat(resolver.relativePath(rootDir, new File("Elsewhere.java"))).isNull();
  }
}