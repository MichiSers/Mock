package christoph;

public class Rate {
    private int rate;
    private RateState state;
    public static enum RateState {
        ATLEAST, ATMOST, TIMES, NEVER
    }
    
    public Rate (RateState state, int rate) {
        this.state = state;
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public RateState getState() {
        return state;
    }
    
    
}

