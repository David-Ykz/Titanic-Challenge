public class Generation {
    private double[] predictionWeights = {-0.1, 0.1, -0.1, 0.1, 0.1, 0.1};
//    private double[] predictionWeights = {-64.99999999999845, 37.40000000000001, -76.99999999999777, 85.84999999999727, -186.85000000000824, -59.74999999999874};
    private int numSuccesses;
    private double deviation;
    private final double WEIGHT_MODIFIER = 0.05;
    private final double MUTATION_CHANCE = 0.9;

    public Generation() {
//        this.numSuccesses = calculateSurvivalDeviation();
        this.deviation = calculateSurvivalDeviation();
    }
    public Generation(Generation oldGeneration) {
        this.predictionWeights = oldGeneration.getPredictionWeights();
        randomModifyWeights();
//        this.numSuccesses = calculateSurvivalDeviation();
        this.deviation = calculateSurvivalDeviation();
    }


    public void randomModifyWeights() {
        int randIndex = (int)(predictionWeights.length * Math.random());
        int sign = 1;
        if (Math.random() > 0.5) {
            sign = -sign;
        }
        if (Math.random() > MUTATION_CHANCE) {
            predictionWeights[randIndex] += sign * WEIGHT_MODIFIER * 10;
        } else {
            predictionWeights[randIndex] += sign * WEIGHT_MODIFIER;
        }
    }

    public int successRate() {
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
    public double calculateSurvivalDeviation() {
        double deviation = 0.0;
        for (Passenger p : Main.passengers) {
            deviation += Math.abs(p.getSurvived() - p.calculateSurvival(predictionWeights));
        }
        return deviation;
    }

    public int getNumSuccesses() {
        return numSuccesses;
    }
    public double getDeviation() {
        return deviation;
    }
    public double[] getPredictionWeights() {
        return predictionWeights;
    }

}
