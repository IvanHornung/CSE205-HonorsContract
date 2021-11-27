public class Vector implements Comparable<Vector> {
    public double xvalue, yvalue;

    public Vector() {
        this.xvalue = 0;
        this.yvalue = 0;
    }

    public Vector(double xvalue, double yvalue) {
        this.xvalue = xvalue;
        this.yvalue = yvalue;
    }

    public void set(double xvalue, double yvalue) {
        this.xvalue = xvalue;
        this.yvalue = yvalue;
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(this.xvalue, 2) + Math.pow(this.yvalue, 2));
    }

    public Vector setMagnitude(double newMagnitude) {
        double currentMagnitude = Math.sqrt(Math.pow(this.xvalue, 2) + Math.pow(this.yvalue, 2));
        this.xvalue *= (newMagnitude/currentMagnitude);
        this.yvalue *= (newMagnitude/currentMagnitude);
        return this;
    }

    public double getXValue() { return this.xvalue; }
    public double getYValue() { return this.yvalue; }

    void add(Vector parent) {
        this.xvalue += parent.getXValue();
        this.yvalue += parent.getYValue();
    }

    void subtract(Vector parent) {
        this.xvalue -= parent.getXValue();
        this.yvalue -= parent.getYValue();
    }

    public void multiply(double multiplier) {
        this.xvalue *= multiplier;
        this.yvalue *= multiplier;
    }

    public void divide(double denominator) {
        this.xvalue /= denominator;
        this.yvalue /= denominator;
    }

    public double dir() {
        return Math.atan2(this.yvalue, this.xvalue);
    }

    public void limit(double maxForce) {
        double magnitude = Math.sqrt(Math.pow(this.xvalue, 2) + Math.pow(this.yvalue, 2));
        double multiplier;
        if(magnitude > maxForce) 
            multiplier = maxForce / magnitude;
        else
            multiplier = 1.0;
        
        this.xvalue *= multiplier;
        this.yvalue *= multiplier;
    }

    public int compareTo(Vector v) {
        return (int)(Math.sqrt(Math.pow(this.xvalue - v.xvalue, 2) + Math.pow(this.yvalue - v.yvalue, 2)));
    }
}
