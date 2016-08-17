package instruments;

public class Cable {

    private boolean free = true;

    public synchronized boolean isFree() {
        return free;
    }

    public synchronized void setFree(boolean free) {
        this.free = free;
    }
}
