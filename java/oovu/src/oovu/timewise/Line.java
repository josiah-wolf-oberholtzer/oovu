package oovu.timewise;


import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.max.MaxObject;

public class Line extends MaxObject implements Executable {
    
    private MaxClock clock = new MaxClock(this);
    private int output_granularity = 20;     
    private float remaining_time = 0;
    private float current_value = 0;
    private float starting_value = 0;
    private float desired_value = 0;
    private float current_slope = 0;
    
    public Line(Atom[] arguments) {
        this.declareIO(1, 1);
        if (0 < arguments.length && ! arguments[0].isString()) {
            this.current_value = arguments[0].toInt(); 
        }
    }
    
    @Override
    public void bang() {
        outlet(0, this.current_value);
    }
    
    @Override
    public void inlet(int value) {
        this.stop();
        this.current_value = value;
        this.outlet(0, value);
    }
    
    @Override
    public void list(int[] value) {
        this.stop();
        this.desired_value = value[0];
        this.remaining_time = value[1];
        this.starting_value = this.current_value;
        this.current_slope = (this.starting_value - this.desired_value) / this.remaining_time;
        this.clock.delay(this.output_granularity);
    }
    
    public void execute() {
        float next_time = this.remaining_time - this.output_granularity;
        if (next_time < 0) {
            next_time = 0;
        }
        float next_value = this.desired_value + (this.current_slope * next_time);
        this.current_value = (int) next_value;
        this.remaining_time = next_time;
        outlet(0, this.current_value);
        if (0 < next_time) {
            this.clock.delay(this.output_granularity);
        }
    }
    
    public void notifyDeleted() {
        this.clock.unset();
        this.clock.release();
    }
    
    public void stop() {
        this.clock.unset();
    }
}
