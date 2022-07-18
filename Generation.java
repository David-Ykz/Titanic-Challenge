public class Generation {
    private double[] predictionWeights = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private int numSuccesses;
    private final double WEIGHT_MODIFIER = 0.05;

    public Generation() {
        this.numSuccesses = calculateSurvivalDeviation();
    }
    public Generation(Generation oldGeneration) {
        this.predictionWeights = oldGeneration.getPredictionWeights();
        randomModifyWeights();
        this.numSuccesses = calculateSurvivalDeviation();
    }


    public void randomModifyWeights() {
        int randIndex = (int)(predictionWeights.length * Math.random());
        int sign = 1;
        if (Math.random() > 0.5) {
            sign = -sign;
        }
        predictionWeights[randIndex] += sign * WEIGHT_MODIFIER * predictionWeights[randIndex];
    }

    public int calculateSurvivalDeviation() {
        int successes = 0;
        for (Passenger p : Main.passengers) {
            if (p.calculateSurvival(predictionWeights) >= 0.5) {
                if (p.getSurvived() == 1) {
                    successes++;
                }
            } else if (p.calculateSurvival(predictionWeights) < 0.5) {
                if (p.getSurvived() == 0) {
                    successes++;
                }
            }
        }
        return successes;
    }

    public int getNumSuccesses() {
        return numSuccesses;
    }
    private double[] getPredictionWeights() {
        return predictionWeights;
    }

}
