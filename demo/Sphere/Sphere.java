public class Sphere {

    private final double PI = 3.1415;
    private int radius;

    public Sphere(int radius) {
        this.radius = radius;
    }

    public int Diameter() {
        int diam = 2 * radius;
        return diam;
    }

    public double Circumference() {
        int diam = Diameter();
        double circ = PI * diam;
        return circ;
    }

    public double Area() {
        double circ = Circumference();
        double area = 2 * radius * circ;
        return area;
    }

    public double Volume() {
        double circ = Circumference();
        double area = Area();
        double smth = circ * area;
        double vol = (1 / (6 * PI)) * smth;
        return vol;
    }

    public static void main(String[] args){
        int radius = 3;
        Sphere s = new Sphere(radius);
        int diam = s.Diameter();
        double circ = s.Circumference();
        double area = s.Area();
        double vol = s.Volume();
    }
}