/*
 * Copyright (c) 2011-2013, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.abst.feature.detect.extract;

import boofcv.struct.QueueCorner;
import boofcv.struct.image.ImageFloat32;


/**
 * <p>
 * Detects features in an intensity image as local maximums.  The extractor can be configured to ignore pixels
 * along the image's border by a user specified distance.  Some implementations require candidate locations for the
 * features.  This allows for a sparse algorithm to be used, resulting in a significant speed boost.  Local maximums
 * with intensity values less than the user specified threshold are ignored and values equal to Float.MAX_VALUE.  A
 * good way to ignore previously detected features is to set their pixel values to Float.MAX_VALUE.
 * </p>
 *
 * <p>
 * An extractor which uses candidate features must always be provided them.  However, an algorithm which does not
 * use candidate features will simply ignore that input and operate as usual.
 * </p>
 *
 * <p>
 * The processing border is defined as the image minus the ignore border.  Some algorithms cannot detect features
 * which are within the search radius of this border.  If that is the case it would be possible to have a feature
 * at the image border.  To determine if this is the case call {@link #canDetectBorder()}.
 * <p>
 *
 * @author Peter Abeles
 */
public interface FeatureExtractor {

	/**
	 * Process a feature intensity image to extract the point features.  If a pixel has an intensity
	 * value == Float.MAX_VALUE it is be ignored.
	 *
	 * @param intensity       Feature intensity image.  Can be modified.
	 * @param candidate       Optional list of candidate features computed with the intensity image.
	 * @param foundFeature    Storage for found features
	 */
	public void process(ImageFloat32 intensity, QueueCorner candidate, QueueCorner foundFeature);

	/**
	 * Returns true if the algorithm requires a candidate list of corners.
	 *
	 * @return true if candidates are required.
	 */
	public boolean getUsesCandidates();

	/**
	 * Features must have the specified threshold.
	 *
	 * @return threshold for feature selection
	 */
	public float getThreshold();

	/**
	 * Change the feature selection threshold.
	 *
	 * @param threshold The new selection threshold.
	 */
	public void setThreshold(float threshold);

	/**
	 * Pixels which are within 'border' of the image border will not be considered.
	 *
	 * @param border Border size in pixels.
	 */
	public void setIgnoreBorder(int border);

	/**
	 * Returns the size of the image border which is not processed.
	 *
	 * @return border size
	 */
	public int getIgnoreBorder();

	/**
	 * Can it detect features which are inside the image border.  For example if a feature
	 * has a radius of 5, but there is a local max at 2, should that be returned?  Can
	 * the output handle feature descriptors which are partially inside the image?
	 *
	 * @return If it can detect features inside the image border.
	 */
	public boolean canDetectBorder();

	/**
	 * Species the search radius for the feature
	 *
	 * @param radius Radius in pixels
	 */
	public void setSearchRadius(int radius);

	/**
	 * Describes how large the region is that is being searched.  The radius is the number of
	 * pixels away from the center.
	 *
	 * @return Search radius
	 */
	public int getSearchRadius();
}
