# Titanic Competition - Predicting Disaster Using Logistic Regression


### Table of Contents:



1. Overview
2. Data cleaning and processing
3. Selecting a model
4. Implementation
5. Optimization
6. Comparisons


### Overview

The Titanic Competition is a competition hosted by Kaggle, where participants are tasked with predicting the survival of a group of passengers. Participants are given a training dataset of around 900 passengers with features like age, gender, passenger class, and if they survived or not. They are then given a testing dataset of around 400 passengers, except without any information on their survival. Using patterns from the training data, the goal is to correctly predict the survival of as many passengers in the testing set. 


### Data Cleaning And Processing

Both training and testing data come in 2 separate .csv files, with each column describing a characteristic (ex. age). Shown below is an excerpt of the training data. Note that for the _Survived_ column, a 0 indicates the person did not survive, and a 1 indicates they did survive. 

![image](https://github.com/David-Ykz/Titanic-Challenge/assets/59211419/f719cbee-af09-4b7d-b751-ebeb97682682)

The first step in processing this data is to remove unnecessary columns. _Name_, _Ticket_, and _Cabin_ were all removed as they were unrelated to the problem and do not provide any meaningful information. _Cabin_ data could potentially be mapped to locations on the ship and their subsequent chances of survival, but missing data meant it was not worth the additional complexity. The _PassengerId_ was also not considered during analysis, and simply remains to keep track of each passenger. 

Secondly, columns with text were converted into their numerical equivalent; _Gender_ was represented with a 0 for male and 1 for female, _Embarked_ used 0, 1, 2 for Queenstown, Cherbourg, and Southampton respectively. 

Thirdly, any missing data (mostly in _Age_) was filled in with the mean of the already existing data. Both training and testing data had their own individual mean used to fill any missing data. 

Finally, the data was normalized with the formula $$\frac{x-\mu}{\sigma}$$ Where $x$ is a data point, $\mu$ is the mean of the column, and $\sigma$ is the standard deviation of the column. This ensures that the data is relatively close to 0, preventing a single parameter from dominating the model. 

These steps were implemented in [Python](https://github.com/David-Ykz/Titanic-Python-Libraries) using dataframe (pandas library). Once the data was processed and cleaned, it was written to another .csv file to be used.


### Selecting A Model

Given that the task at hand is a classification problem, where the possible outputs are either 0 or 1, the most suitable model was logistic regression. 

Logistic regression is a model that predicts the probability of a binary event happening, outputting a number from 0 to 1. In the context of the problem, an output of 0 means that the probability of a person surviving is 0, and vice versa for an output of 1. For this problem, outputs greater than or equal to 0.5 were interpreted that the passenger survived. 


### Implementation

The model requires 3 main components to work



1. A method of predicting a person’s survival
2. A cost function that describes the error of the prediction with the actual result
3. A method to adjust the parameters to minimize error

To predict a person’s survival (known as $\hat{y}$), the model first needs to identify the importance of each feature. For example, a person’s gender may be much more significant than their fare price, and thus will subsequently be weighted higher. As the model is a regression, the formula for $\hat{y}$ is given by $wx+b$, where:

$w$ represents the weights - an array where each value represents the importance of each feature  
$x$ represents an input value (in this case a passenger) - an array where each value represents a unique feature (ex. age, gender, passenger class)  
$b$ represents the bias - a value that is similar to a y-intercept  

Since both $w$ and $x$ are arrays with the same size, their multiplication is simply the dot product of the two: $[w_{1}, w_{2},w_{3}]\cdot[x_{1}, x_{2},x_{3}]=w_{1}x_{1}+w_{2}x_{2}+w_{3}x_{3}$

To ensure the resulting $\hat{y}$ is between 0 and 1, the result is compressed using the sigmoid function: $$\frac{1}{1+e^{-x}}$$

To measure error, we will use logistic error, which is given by $L=ylog(\hat{y})+(1-y)log(1-\hat{y})$ (note that log is the natural logarithm)

To understand this formula, we will first consider the 2 cases where the result $y$ is either 0 or 1:

$$ \begin{cases} \hat{y} & \quad \text{if } y=1 \\
1-\hat{y} & \quad \text{if } y=0 \end{cases} $$

This can be combined into one equation using the fact that $a^{y}b^{1-y}=a \textup{ when } y=1 \textup{ and } a^{y}b^{1-y}=b \textup{ when } y=0 \quad a,b \in \mathbb{R}$

As a result, our error function is $$\hat{y}^{y}(1-\hat{y})^{1-y}$$

However, using this error function would result in multiple local minimums and would not be effective in training an accurate model. Using the property that logarithmic functions are all monotonic, we can take the natural logarithm of the error function:

$$log(\hat{y}^{y}(1-\hat{y})^{1-y})=log(\hat{y}^{y})+log((1-\hat{y})^{1-y})=ylog(\hat{y})+(1-y)log(1-\hat{y})$$

To find the total loss, we sum up the logarithmic error for each training sample and average it. The goal of our model will be to minimize the function for total loss by adjusting the various parameters. To do that, we will use gradient descent, which involves using the first derivative of the cost (with respect to each parameter) to find the shortest path to minimizing cost. 

We will iterate through each parameter and update its gradient with the following formula: 

$$\frac{\partial L}{\partial w_{j}}=\frac{1}{m}\sum_{i=1}^{m}x_{j}^{i}(y-\hat{y})$$

Where $m$ is the number of training samples, $w_{j}$ represents each parameter and $x_{j}^{i}$ represents the feature (for each training sample $i$) that corresponds to the parameter. 

This formula is derived from taking the derivative of the Loss function, repeatedly applying the chain rule. One useful derivative to know is of the sigmoid function:

$${\sigma}'(x)=\sigma(x)(1-\sigma(x))$$

To update each parameter, we subtract it by the gradient multiplied by the learning rate (an arbitrary constant that plays a factor in how fast the algorithm learns):

$$w_{j}=w_{j} -\alpha \frac{\partial L}{\partial w_{j}}$$

With this, the model is now able to improve over time, scoring 73.4% on the Kaggle submission. As expected, the algorithm performs worse on testing data compared to training data (79% vs 73%) but overall scores decently.


### Optimization

The main improvements to the model came from using momentum and tuning hyperparameters. Momentum is an additional feature that helps the model avoid falling into local minimums by giving the gradients additional velocity. Instead of directly updating the weights, we instead add part of the previous gradient to the current gradient: $$w_{j}=\alpha(\mu b_{i-1}+g_{i})$$

Here $\mu$ is the constant for momentum, $b_{i-1}$ is the previous gradient, and $g_{i}$ is the current gradient.

Hyperparameters are just the various constants that the model uses, such as learning rate, number of epochs (iterations), number of features, or momentum. I found that the combination that worked the best was &lt;80,000 epochs with a learning rate of 0.001. With more iterations, the model started overfitting or falling into local minimums. Although momentum did not significantly contribute to improving accuracy, having $\mu=0.9$ seemed to work the best. Finally, it was surprising to find that using only 3 features had the best accuracy (_Age_, _Gender_, and _Passenger class_). Introducing additional features like _Fare_ or _Embarked_ actually lowered the accuracy, and no combination of the other features seemed to improve it. 


### Comparisons

Compared to other models, logistic regression is a relatively simple model, but still performs well compared to other models. When compared to the models from [Scikit’s Library](https://scikit-learn.org/stable/supervised_learning.html#supervised-learning), this implementation is able to perform at a similar accuracy, with only a ~2% gap between the best model. Based on others’ experiences, there exists a threshold for these models at 77.751% accuracy. In order to achieve higher accuracy, a significantly more complex model is required, with extensive feature engineering and correlation tables. 


<table>
  <tr>
   <td>Model Type
   </td>
   <td>Training Accuracy
   </td>
   <td>Testing Accuracy
   </td>
  </tr>
  <tr>
   <td>Decision Tree (Scikit Library)
   </td>
   <td>100.0%
   </td>
   <td>72.25%
   </td>
  </tr>
  <tr>
   <td>Logistic Regression (My implementation)
   </td>
   <td>79.01%
   </td>
   <td>75.84%
   </td>
  </tr>
  <tr>
   <td>Random Forest (Scikit Library)
   </td>
   <td>100.0%
   </td>
   <td>75.84%
   </td>
  </tr>
  <tr>
   <td>Logistic Regression (Scikit Library)
   </td>
   <td>80.13%
   </td>
   <td>76.32%
   </td>
  </tr>
  <tr>
   <td>Random Forest, capped at depth = 5
   </td>
   <td>86.14%
   </td>
   <td>77.75%
   </td>
  </tr>
</table>


The relatively low training accuracy of both logistic regression models (this implementation and Scikit’s model) suggests that logistic regression simply isn’t complex enough to fully identify patterns within the Titanic problem. Both the Random Forest and Decision Tree models were able to fit the data given their 100% training accuracy (although this entails that both models overfit the data), suggesting that a branched/layered model is needed to properly capture the data. 

While logistic regression may not offer the highest accuracy compared to other models, it still performs to a remarkable degree, while offering a simple and intuitive introduction into machine learning and data science. 
