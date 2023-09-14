import pandas as pd
import numpy as np
from sklearn import tree
from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import RandomForestClassifier
import csv

np.set_printoptions(threshold=np.inf)

def genderToNum(cell):
    return 0 if cell == "male" else 1

def embarkedToNum(cell):
    if cell == "Q":
        return 0
    elif cell == "S":
        return 1
    else:
        return 2

# Load training dataset
trainDf = pd.read_csv("ScaledTrainingSet.csv")
trainingData = trainDf.to_numpy()
results = pd.read_csv("ResultsSet.csv")
results = results.drop(columns=["PassengerId"]).to_numpy()
results = results.ravel()
# Load testing dataset
testDf = pd.read_csv("ScaledTestingSet.csv")
testingData = testDf.to_numpy()

# Instantiate models
decisionTree = tree.DecisionTreeClassifier()
logisticRegression = LogisticRegression(max_iter=100000)
randomForest = RandomForestClassifier()
randomForestCapped = RandomForestClassifier(max_depth=5)

# Train each model
decisionTree.fit(trainingData, results)
logisticRegression.fit(trainingData, results)
randomForest.fit(trainingData, results)
randomForestCapped.fit(trainingData, results)

# Saves each model's prediction in separate .csv files
print("Decision Tree Training Accuracy: " + str(round(decisionTree.score(trainingData, results) * 100, 2)))
treeFile = open("treePredictions.csv", "w", newline="")
output = csv.writer(treeFile)
output.writerow(["PassengerId"] + ["Survived"])
passengerId = 892
for i in decisionTree.predict(testingData):
    output.writerow([passengerId] + [i])
    passengerId += 1
treeFile.close()

print("Logistic Regression Training Accuracy: " + str(round(logisticRegression.score(trainingData, results) * 100, 2)))
logisticFile = open("logisticPredictions.csv", "w", newline="")
output = csv.writer(logisticFile)
output.writerow(["PassengerId"] + ["Survived"])
passengerId = 892
for i in logisticRegression.predict(testingData):
    output.writerow([passengerId] + [i])
    passengerId += 1
logisticFile.close()

print("Random Forest Training Accuracy: " + str(round(randomForest.score(trainingData, results) * 100, 2)))
forestFile = open("forestPredictions.csv", "w", newline="")
output = csv.writer(forestFile)
output.writerow(["PassengerId"] + ["Survived"])
passengerId = 892
for i in randomForest.predict(testingData):
    output.writerow([passengerId] + [i])
    passengerId += 1
forestFile.close()

print("Random Forest (Capped) Training Accuracy: " + str(round(randomForestCapped.score(trainingData, results) * 100, 2)))
cappedForestFile = open("cappedForestPredictions.csv", "w", newline="")
output = csv.writer(cappedForestFile)
output.writerow(["PassengerId"] + ["Survived"])
passengerId = 892
for i in randomForestCapped.predict(testingData):
    output.writerow([passengerId] + [i])
    passengerId += 1
cappedForestFile.close()



