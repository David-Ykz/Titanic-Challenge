public class Generation {
    // pClass, isFemale, child, elderly, numSiblings, numParents
    private double[] predictionWeights = {0.25, 0.15, 0.2, 0, 0.025, 0.025};
//    private double[] predictionWeights = {0.3, 0.12, 0.21, 0.07, 0.05, 0.07};
//    private double[] predictionWeights = {0.2, 0.05, 0.2, 0.07, 0.04, 0.08};
    private int numSuccesses;
    private final double WEIGHT_MODIFIER = 0.001;

    public Generation() {
        this.numSuccesses = successRate();
    }
    public Generation(Generation parentA, Generation parentB, int crossoverPoint) {
        for (int i = 0; i < predictionWeights.length; i++) {
            if (i >= crossoverPoint) {
                predictionWeights[i] = parentB.predictionWeights[i];
            } else {
                predictionWeights[i] = parentA.predictionWeights[i];
            }
        }
        this.numSuccesses = successRate();
    }

    public void randomModifyWeights() {
        int randIndex = (int)(predictionWeights.length * Math.random());
        int sign = 1;
        if (predictionWeights[randIndex] < WEIGHT_MODIFIER) {
            sign = 1;
        } else if (predictionWeights[randIndex] > 1) {
            sign = -1;
        } else if (Math.random() > 0.5) {
            sign = -sign;
        }
        predictionWeights[randIndex] += sign * WEIGHT_MODIFIER;
    }
    public void randomModifyWeights(int randIndex) {
        int sign = 1;
        if (predictionWeights[randIndex] < WEIGHT_MODIFIER) {
            sign = 1;
        } else if (predictionWeights[randIndex] > 1) {
            sign = -1;
        } else if (Math.random() > 0.5) {
            sign = -sign;
        }
        predictionWeights[randIndex] += sign * WEIGHT_MODIFIER;
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

    public int getNumSuccesses() {
        return numSuccesses;
    }
    public double[] getPredictionWeights() {
        return predictionWeights;
    }

}
