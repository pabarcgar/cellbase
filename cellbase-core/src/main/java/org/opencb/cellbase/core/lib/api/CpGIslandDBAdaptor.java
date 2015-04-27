/*
 * Copyright 2015 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.cellbase.core.lib.api;

import org.opencb.biodata.models.core.CpGIsland;
import org.opencb.biodata.models.feature.Region;
import org.opencb.cellbase.core.common.IntervalFeatureFrequency;

import java.util.List;


public interface CpGIslandDBAdaptor {
	
	public List<CpGIsland> getAllByRegion(Region region);
	
	public List<List<CpGIsland>> getAllByRegionList(List<Region> regionList);
	
	
	public List<IntervalFeatureFrequency> getAllIntervalFrequencies(Region region, int interval);
}
