#!/bin/bash

git checkout main;git fetch;git pull;git branch -D dev;git checkout -b dev;git cherry-pick ea13d7180b08088176150d3f1b808fa5126349b3;