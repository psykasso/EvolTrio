/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evomusic.evoltrio.ui;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

import org.apache.pivot.charts.LineChartView;

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */

 
public class TestChart implements Application {
    private Window window = null;
 
    @Override
    public void startup(Display display, Map<String, String> properties)
        throws Exception {
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (Window)bxmlSerializer.readObject(TestChart.class, "TestChart.bxml");
        window.setContent(new LineChartView());
        window.open(display);
    }
 
    @Override
    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        }
 
        return false;
    }
 
    @Override
    public void suspend() {
    }
 
    @Override
    public void resume() {
    }
}
