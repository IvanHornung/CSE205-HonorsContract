public class BlinkingBlock extends Block {
    private boolean isDarker;
    private long timesRendered;

    public BlinkingBlock(int xPos, int yPos, int column) {
        super(xPos, yPos, column);
        this.isDarker = false;
        this.timesRendered = 0;
    }

    @Override
    public void render() {
        timesRendered++;
        if(timesRendered % 50 == 0) {
            isDarker = !isDarker;
            if(!isDarker)
                this.objectColor = this.objectColor.darker();
            else
                this.objectColor = this.objectColor.brighter();
        }
    }

}
