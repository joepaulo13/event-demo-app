#!/bin/bash

git checkout main;git fetch;git pull;git branch -D prod;git checkout -b prod;git cherry-pick fcbd3b71aca67e70855eb42d72f2ad037694c309;