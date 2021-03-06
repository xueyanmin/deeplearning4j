/*
 *  ******************************************************************************
 *  *
 *  *
 *  * This program and the accompanying materials are made available under the
 *  * terms of the Apache License, Version 2.0 which is available at
 *  * https://www.apache.org/licenses/LICENSE-2.0.
 *  *
 *  *  See the NOTICE file distributed with this work for additional
 *  *  information regarding copyright ownership.
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *  *
 *  * SPDX-License-Identifier: Apache-2.0
 *  *****************************************************************************
 */

package org.nd4j.jita.allocator.time;

/**
 * @author raver119@gmail.com
 */
public interface RateTimer {

    /**
     * This method notifies timer about event
     */
    void triggerEvent();

    /**
     * This method returns average frequency of events happened within predefined timeframe
     * @return
     */
    double getFrequencyOfEvents();

    /**
     * This method returns total number of events happened withing predefined timeframe
     * @return
     */
    long getNumberOfEvents();
}
