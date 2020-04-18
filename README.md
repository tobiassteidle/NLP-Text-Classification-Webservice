# NLP-Text-Classification-Webservice
Provide a web service that uses the trained model from the project [NLP Text Classification](https://github.com/tobiassteidle/NLP-Text-Classification).

## Installation - Pretrained Model
Make sure you have Java Runtime 8+ installed.
Extract `pretrained/tensorflow_nlp.zip` into `models-tensorflow-work/`:
```
cd models-tensorflow-work/ && unzip ../pretrained/tensorflow_nlp.zip && cd ..
```

Expected directory structure:
```
NLP-Text-Classification-Webservice/models-tensorflow-work/tensorflow  
NLP-Text-Classification-Webservice/models-tensorflow-work/tensorflow/variables
NLP-Text-Classification-Webservice/models-tensorflow-work/tensorflow/variables/variables.index
NLP-Text-Classification-Webservice/models-tensorflow-work/tensorflow/variables/variables.data-00000-of-00002
NLP-Text-Classification-Webservice/models-tensorflow-work/tensorflow/variables/variables.data-00001-of-00002
NLP-Text-Classification-Webservice/models-tensorflow-work/tensorflow/saved_model.pb
NLP-Text-Classification-Webservice/models-tensorflow-work/tensorflow/assets
```

## Run Service and perform news classfication
```
mvn spring-boot:run
```

Scripted curl:
```
client/predict-curl.sh
```

Explicit curl command:
```
curl -XPOST -H 'Content-Type: application/json' \
 -d '{"newsLine":"Read about Hollywood new born stars"}' \
  http://localhost:8080/nlpclass/news-category/prediction
```
