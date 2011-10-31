/*
 * Copyright (c) 2009 VMware, Inc.
 * Copyright 2009 G. Brown, ixnay.biz.
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
package gr.evomusic.evoltrio.ui.chart;

import org.apache.pivot.charts.ChartView;
import org.apache.pivot.collections.List;
import org.apache.pivot.json.JSON;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.Window;

public class ChartsDemo extends Window {
    public void showSelectionMessage(ChartView chartView, int x, int y) {
        ChartView.Element element = chartView.getElementAt(x, y);

        if (element != null) {
            ChartView.CategorySequence categories = chartView.getCategories();
            int elementIndex = element.getElementIndex();

            String elementLabel;
            if (categories.getLength() > 0) {
                elementLabel = "\"" + chartView.getCategories().get(elementIndex).getLabel() + "\"";
            } else {
                elementLabel = Integer.toString(elementIndex);
            }

            List<?> chartData = chartView.getChartData();
            Object series = chartData.get(element.getSeriesIndex());

            String seriesNameKey = chartView.getSeriesNameKey();
            Prompt.prompt("You clicked element " + elementLabel + " in \""
                + JSON.get(series, seriesNameKey) + "\".", this);
        }
    }
}
