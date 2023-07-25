import java.util.Arrays;

public class LogisticRegression {
    public double[] weights;
    public double bias;
    public double[] weightGradients;
    public double biasGradient;
    public double[] prevGradients;
    public double[][] dataSet;
    public int[] results;

    LogisticRegression(double[][] trainingData, int[] results) {
        this.dataSet = trainingData;
        this.results = results;
        weights = initializeParameters(trainingData[0].length, 0);
        bias = 0;
        weightGradients = initializeParameters(trainingData[0].length, 0);
        prevGradients = initializeParameters(trainingData[0].length, 0);
    }
    public static double dotProduct(double[] arr1, double[] arr2) {
        double result = 0;
        for (int i = 0; i < arr1.length; i++) {
            result += arr1[i] * arr2[i];
        }
        return result;
    }
    // Fills an array with a specified value (if value is -1, it will fill it with random numbers from 0 to 1)
    public static double[] initializeParameters(int num, double fillValue) {
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
    public static double sigmoid(double num) {
        double result = 1 / (1 + Math.exp(-num));
        if (result == 1.0) {
            result -= 0.00;
        }
        return result;
    }
    public static double logarithmicError(double y, double yHat) {
        return y * Math.log(yHat) + (1 - y) * Math.log(1 - yHat);
    }
    public double prediction(double[] data) {
        return sigmoid(dotProduct(weights, data) + bias);
    }
    public double logarithmicLoss() {
        double sum = 0;
        for (int i = 0; i < dataSet.length; i++) {
            double[] data = dataSet[i];
            double yHat = prediction(data);
            double y = results[i];
            sum += logarithmicError(y, yHat);
        }
        return -1 / (double)dataSet.length * sum;
    }
    // Applies gradient descent to help the model improve every time the method is called
    public double learn(double learningRate, double momentum) {
        for (int j = 0; j < weights.length; j++) {
            double sum = 0;
            for (int i = 0; i < dataSet.length; i++) {
                sum += dataSet[i][j] * (results[i] - prediction(dataSet[i]));
            }
            weightGradients[j] = 1.0/dataSet.length * sum;
        }
        double sum = 0;
        for (int i = 0; i < dataSet.length; i++) {
            int y = results[i];
            double yHat = prediction(dataSet[i]);
            sum += (-(y/yHat) + (1-y)/(1-yHat)) * yHat * (1 - yHat);
        }
        biasGradient = -1.0/ dataSet.length * sum;
//        biasGradient = 0;

        for (int i = 0; i < weights.length; i++) {
            prevGradients[i] = momentum * prevGradients[i] + weightGradients[i];
            weights[i] += learningRate * prevGradients[i];
        }
        bias += learningRate * biasGradient;
        return bias;
    }
    // Checks the accuracy of the model to see how many it correctly predicted
    public int testPredictions(double[][] testingData, int[] outputs) {
        int numCorrect = 0;
        for (int i = 0; i < testingData.length; i++) {
            double[] data = testingData[i];
            if (Math.abs(outputs[i] - prediction(data)) < 0.5) {
                numCorrect++;
            }
        }
        return numCorrect;
    }
}
