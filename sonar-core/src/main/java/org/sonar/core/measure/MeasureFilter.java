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
package org.sonar.core.measure;

import com.google.common.collect.Lists;
import org.sonar.api.measures.Metric;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MeasureFilter {
  // conditions on resources
  private String baseResourceKey;
  private boolean onBaseResourceChildren = false; // only if getBaseResourceKey is set
  private List<String> resourceScopes = Lists.newArrayList();
  private List<String> resourceQualifiers = Lists.newArrayList();
  private List<String> resourceLanguages = Lists.newArrayList();
  private String resourceName;
  private Date fromDate = null, toDate = null;
  private boolean userFavourites = false;

  // conditions on measures
  private List<MeasureFilterCondition> measureConditions = Lists.newArrayList();

  // sort
  private MeasureFilterSort sort = new MeasureFilterSort();

  public String getBaseResourceKey() {
    return baseResourceKey;
  }

  public MeasureFilter setBaseResourceKey(String s) {
    this.baseResourceKey = s;
    return this;
  }

  public MeasureFilter setOnBaseResourceChildren(boolean b) {
    this.onBaseResourceChildren = b;
    return this;
  }

  public boolean isOnBaseResourceChildren() {
    return onBaseResourceChildren;
  }

  public MeasureFilter setResourceScopes(@Nullable List<String> l) {
    this.resourceScopes = (l != null ? l : Collections.<String>emptyList());
    return this;
  }

  public MeasureFilter setResourceQualifiers(List<String> l) {
    this.resourceQualifiers = (l != null ? l : Collections.<String>emptyList());
    return this;
  }

  public MeasureFilter setResourceQualifiers(String... l) {
    this.resourceQualifiers = Lists.newArrayList(l);
    return this;
  }

  public MeasureFilter setResourceLanguages(List<String> l) {
    this.resourceLanguages = (l != null ? l : Collections.<String>emptyList());
    return this;
  }

  public MeasureFilter setResourceLanguages(String... l) {
    this.resourceLanguages = Lists.newArrayList(l);
    return this;
  }

  public MeasureFilter setUserFavourites(boolean b) {
    this.userFavourites = b;
    return this;
  }

  public boolean isOnFavourites() {
    return userFavourites;
  }

  public String getResourceName() {
    return resourceName;
  }

  public MeasureFilter setResourceName(String s) {
    this.resourceName = s;
    return this;
  }

  public MeasureFilter addCondition(MeasureFilterCondition condition) {
    this.measureConditions.add(condition);
    return this;
  }

  public MeasureFilter setSortOn(MeasureFilterSort.Field sortField) {
    this.sort.setField(sortField);
    return this;
  }

  public MeasureFilter setSortAsc(boolean b) {
    this.sort.setAsc(b);
    return this;
  }

  public MeasureFilter setSortOnMetric(Metric m) {
    this.sort.setField(MeasureFilterSort.Field.METRIC);
    this.sort.setMetric(m);
    return this;
  }

  public MeasureFilter setSortOnPeriod(int period) {
    this.sort.setPeriod(period);
    return this;
  }

  public MeasureFilter setFromDate(Date d) {
    this.fromDate = d;
    return this;
  }

  public MeasureFilter setToDate(Date d) {
    this.toDate = d;
    return this;
  }

  public Date getFromDate() {
    return fromDate;
  }

  public Date getToDate() {
    return toDate;
  }

  public List<String> getResourceScopes() {
    return resourceScopes;
  }

  public List<String> getResourceQualifiers() {
    return resourceQualifiers;
  }

  public List<String> getResourceLanguages() {
    return resourceLanguages;
  }

  public List<MeasureFilterCondition> getMeasureConditions() {
    return measureConditions;
  }

  MeasureFilterSort sort() {
    return sort;
  }

}