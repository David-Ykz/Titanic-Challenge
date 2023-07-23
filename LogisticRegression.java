import java.util.Arrays;

public class LogisticRegression {
    public static double dotProduct(double[] arr1, double[] arr2) {
        double result = 0;
        for (int i = 0; i < arr1.length; i++) {
            result += arr1[i] * arr2[i];
        }
        return result;
    }

    public double[] initializeParameters(int num, double fillValue) {
        double[] parameters = new double[num];
        if (fillValue >= 0) {
            Arrays.fill(parameters, fillValue);
        } else {
            for (int i = 0; i < num; i++) {
                parameters[i] = Math.random() + 0.01;
            }
        }
        return parameters;
    }

    public double sigmoid(double num) {
        double result = 1 / (1 + Math.exp(-num));
        if (result == 1.0) {
            result -= 0.00;
        }
        return result;
    }

    public double prediction(double[] weights, double bias, double[] data) {
        return sigmoid(dotProduct(weights, data) + bias);
    }

    public double meanSquaredError(double y, double yHat) {
        return (y - yHat) * (y - yHat);
    }

    public double logarithmicError(double y, double yHat) {
        double result = y * Math.log(yHat) + (1 - y) * Math.log(1 - yHat);
        return result;
    }

    public double logarithmicLoss(double[] weights, double bias, double[][] dataSet, int[] results) {
        double sum = 0;
        for (int i = 0; i < dataSet.length; i++) {
            double[] data = dataSet[i];
            double yHat = prediction(weights, bias, data);
//            System.out.println("Prediction " + Double.toString(yHat));
            double y = results[i];
            sum += logarithmicError(y, yHat);
        }
        return -1 / (double)dataSet.length * sum;
    }

    public double learn(double[] weights, double bias, double[][] dataSet, int[] results, double delta, double learningRate) {
        double[] weightGradients = new double[weights.length];
        double biasGradient;

        for (int j = 0; j < weights.length; j++) {
            double sum = 0;
            for (int i = 0; i < dataSet.length; i++) {
                sum += dataSet[i][j] * (results[i] - prediction(weights, bias, dataSet[i]));
            }
            weightGradients[j] = 1.0/dataSet.length * sum;
        }

//        for (int i = 0; i < weights.length; i++) {
//            double currentError = logarithmicLoss(weights, bias, dataSet, results);
//            weights[i] += delta;
//            double deltaError = currentError - logarithmicLoss(weights, bias, dataSet, results);
//            weights[i] -= delta;
//            double slope = deltaError/delta;
//            weightGradients[i] = slope;
//        }



        double deltaError = logarithmicLoss(weights, bias, dataSet, results) - logarithmicLoss(weights, bias + delta, dataSet, results);
        double slope = deltaError/delta;
        biasGradient = slope;

        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * weightGradients[i];
        }
        bias += learningRate * biasGradient;
        return bias;
    }

    public int testPredictions(double[] weights, double bias, double[][] dataSet, int[] results) {
        int numCorrect = 0;
        for (int i = 0; i < dataSet.length; i++) {
            double[] data = dataSet[i];
            if (Math.abs(results[i] - prediction(weights, bias, data)) < 0.5) {
                numCorrect++;
            }
        }
        return numCorrect;
    }

    public double[] allPredictions(double[] weights, double bias, double[][] dataSet) {
        double[] predictions = new double[dataSet.length];
        for (int i = 0; i < dataSet.length; i++) {
            predictions[i] = prediction(weights, bias, dataSet[i]);
        }
        return predictions;
    }
}
