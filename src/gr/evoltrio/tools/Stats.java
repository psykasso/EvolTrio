package gr.evoltrio.tools;

import gr.evoltrio.fitness.FiltersFactory;
import gr.evoltrio.fitness.FiltersFactory.Filter;
import gr.evoltrio.ui.EvolutionTask;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Stats {
    
    private static Stats INSTANCE;
    
    private static Map<FiltersFactory.Filter, BigDecimal> stats; 
    
    private Stats() {
        stats = new HashMap<FiltersFactory.Filter, BigDecimal>();
        
        //initialize
        for(FiltersFactory.Filter filter : FiltersFactory.Filter.values())
            stats.put(filter, new BigDecimal(0));
    }
    
    public static Stats getInstance() {
        if( INSTANCE == null) {
            INSTANCE = new Stats();
        }
        return INSTANCE;
    }
    
    public void reset() {
        new Stats();
    }
    
    public void add(FiltersFactory.Filter filter, double value ) {
        
        stats.put(filter, stats.get(filter).add(new BigDecimal(value)));
    }
    
    public Map<Filter, BigDecimal> getStats() {
        return stats;
    }

    public void printStats(int iteration) {
        String str = "";
        for(FiltersFactory.Filter filter : stats.keySet())
            str += filter + " -- " + stats.get(filter).divide(new BigDecimal((iteration != 0) ? iteration : 1),5, BigDecimal.ROUND_FLOOR).doubleValue() + "\n";
        
        System.out.println(str);
        
    }
    
    public static void main(String[] args) {
        Stats.getInstance().add(FiltersFactory.Filter.ROOTNOTE, 0.01);
        System.out.println(Stats.getInstance());
        Stats.getInstance().reset();
        System.out.println(Stats.getInstance());
    }

}
