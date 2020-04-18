#!/bin/bash

curl -s -XPOST -H 'Content-Type: application/json' -d '{"newsLine":"Read about Hollywood new born stars"}' http://localhost:8080/nlpclass/news-category/prediction

